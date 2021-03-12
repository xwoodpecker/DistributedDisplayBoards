import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {store} from '@/main';
import moment from 'moment';

const backendEndpoint = 'http://localhost:8000/backend';

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

          if(_hasBoardStateChanged(state.boards)){
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
    this.stompClient.connect(
      JSON.parse(JSON.stringify(store.getters.authHeader)),
      frame => {
        this.connected = true;
        
        for (let board of this.boards) {
          //todo get all board messages here  todo todo maybe send automatically??
          this.stompClient.subscribe("/topic/boards." + board.id, message => {
            this._handleIncomingMessages(JSON.parse(message.body))
          });
        }
      },
      error => {
        console.log(error);
        this.connected = false;
      }
    );
  }

  addMessage(content, boardId, displayTime, endDate, bgColor, active){
    let userId = store.getters.getUser.id;
    endDate = this._formatDate(endDate);
    //very javascript, much cool
    const msg = {content, userId, boardId, displayTime, endDate, bgColor, active}
    this._send("message", msg);
  }

  updateMessage(messageId, content, boardId, displayTime, endDate, bgColor, active){
    let userId = store.getters.getUser.id;
    endDate = this._formatDate(endDate);

    const msg = {messageId, content, userId, boardId, displayTime, endDate, active, bgColor}
    this._send("message", msg);
  }

  _send(endpoint, msg) {
    console.log("sending msg " + msg);
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(`/app/${endpoint}`, JSON.stringify(msg));
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
    return true;
  }

  _handleIncomingMessages(messages){
    //todo
  }
}
