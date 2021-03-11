import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {store} from '@/main';

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
    this.connect();
    store.subscribe((mutation, state) => {
      console.log("ding");
      if(mutation.type === "addBoards" || mutation.type === "clearBoards"){
        console.log(state.boards);
        if(state.boards){
          this.boards = state.boards;
        } else {
          this.boards = [];
        }
        
        //todo dont do this when only messages have changed
        //this.reconnect();
        if(!this.connected)
          this.connect();
      }
    })
  }


  connect() {
    this.stompClient.connect(
      JSON.parse(JSON.stringify(store.getters.authHeader)), //if auth headers are injected here, does that work? (after evaluation: not needed probably)
      frame => {
        this.connected = true;
        for (let board of this.boards) {
          console.log("X: " + board + "| " + board.id);
          this.stompClient.subscribe("/topic/boards." + board.id, tick => { //url not correct
            console.log(tick);
            console.log(JSON.parse(tick.body).content);
            //todo set this.boards[board.id].messages to current messages
          });
        }
      },
      error => {
        console.log(error);
        this.connected = false;
      }
    );
  }

  send(message) {
    if (this.stompClient && this.stompClient.connected) {
      const msg = {
        content: message.content,
        user: {id:4},
        board: {id:3},
        endDate: message.showUntil,
        active: true,
        bgColor: message.bgColor
      }
      console.log(JSON.stringify(msg));
      this.stompClient.send("/app/message", JSON.stringify(msg));
    }
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
    this.connected = false;
  }

  reconnect() {
    this.disconnect();
    this.connect();
  }
}
