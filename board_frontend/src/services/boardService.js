import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {store} from '@/main';
import moment from 'moment';
import { ENV } from '@/environment';


const backendEndpoint = ENV.stompBaseUrl;

export default class BoardService {
  socket;
  stompClient;
  connected = false;
  boards;

  constructor() {
    this.socket = new SockJS(backendEndpoint);
    this.stompClient = Stomp.over(this.socket);

    this.boards = store.getters.boards;
    this._connect();

    store.subscribe((mutation, state) => {
      if(mutation.type === "addBoards" || mutation.type === "clearBoards"){
        if(state.boards){
          this.boards = state.boards;
          console.log("checking");
          if(this._hasBoardStateChanged(state.boards)){
            console.log("state changed");
            console.log(state.boards)
            this._reconnect();
          }
        } else {
          this.boards = [];
        }

        if(!this.connected)
          this._connect();
      }
    })
  }

  _connect() {
    if(this.boards){
      this.stompClient.connect(
        JSON.parse(JSON.stringify(store.getters.authHeader)),
        frame => {
          this.connected = true;
          for (let board of this.boards) {
            this._getActiveMessages(board.id);
            this.stompClient.subscribe("/topic/boards." + board.id, message => {
              this._handleIncomingMessages({id: board.id, messages: JSON.parse(message.body)});
            });
          }
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    }
    else {
      this.connected = false;
    } 
  }

  _getActiveMessages(boardId){
    this._send("getActiveMessages", {id: boardId});
  }

  addMessage(content, boardId, displayTime, endDate, bgColor, active){
    let userId = store.getters.getUser.id;
    //content = JSON.stringify(content);
    endDate = this._formatDate(endDate);
    //very javascript, much cool
    const msg = {content, user: {id: userId}, board: {id: boardId}, displayTime, endDate, bgColor, active}
    console.log(msg)
    this._send("message", msg);
  }

  updateMessage(messageId, content, boardId, displayTime, endDate, bgColor, active){
    let userId = store.getters.getUser.id;
    //content = JSON.stringify(content);
    endDate = this._formatDate(endDate);

    const msg = {id: messageId, content, user: {id: userId}, board: {id: boardId}, displayTime, endDate, active, bgColor}
    this._send("message", msg);
  }

  _send(endpoint, msg) {
    console.log("sending msg " + msg + "to " + endpoint);
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(`/app/${endpoint}`, msg ? JSON.stringify(msg) : undefined);
    } else {
      console.log('tried to send a message, but stomp wasnt connected. trying again in 2 seconds.')
      this._connect();
      setTimeout(_send(endpoint, msg), 2000);
    }
  }

  _disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
    this.connected = false;
  }

  
  _reconnect() {
    this._disconnect();
    this._connect();
  }

  _formatDate(date){
    return moment(date).format('YYYY-MM-DD hh:mm:ss');
  }

  
  _hasBoardStateChanged(boards){
    //todo check if new boards have come in / old ones have been removed
    for(let newBoard of boards){
      if(!this.boards.find(board => board.id == newBoard.id)){
        return true;
      }
    }

    for(let oldBoard of this.boards){
      if(!boards.find(board => board.id == oldBoard.id)){
        return true;
      }
    }

    return false;
  }

  _handleIncomingMessages(board){
    console.log(board);
    store.commit("setMessages", board);
    console.log(store.getters.boards);
  }
}
