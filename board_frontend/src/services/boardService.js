import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {store} from '../main';

const backendEndpoint = 'http://localhost:8000/backend';

export default class BoardService {
  socket;
  stompClient;
  connected = false;
  boards;

  constructor() {
    this.socket = new SockJS(backendEndpoint);
    this.stompClient = Stomp.over(this.socket);

    store.subscribe((mutation, state) => {
      if(mutation.type === "addBoards" || mutation.type === "clearBoards"){
        this.boards = state.boards;
        console.log(this.boards);
        //todo dont do this when only messages have changed
        this.reconnect();
      }
    })
  }


  connect() {
    this.stompClient.connect(
      {}, //if auth headers are injected here, does that work? (after evaluation: not needed probably)
      frame => {
        this.connected = true;
        console.log(frame);
        for (let board in this.boards) {
          console.log(board);
          this.stompClient.subscribe("/boards/" + board.id, tick => { //url not correct
            console.log(tick);
            console.log(JSON.parse(tick.body).content);
            //todo set this.boards[board.id].messages to current messages
          });
          this.send();
        }
      },
      error => {
        console.log(error);
        this.connected = false;
      }
    );
  }

  send() {
    if (this.stompClient && this.stompClient.connected) {
      const msg = {
        content: "content",
        user: 1,
        board: 1,
        ttl: 1234,
        active: true
      }
      console.log(JSON.stringify(msg));
      this.stompClient.send("/app/huso/message", JSON.stringify(msg));
    }
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
    this.connected = false;
  }

  reconnect() {
    //this.disconnect();
    //this.connect();
  }
}