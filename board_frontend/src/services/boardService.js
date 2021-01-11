import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const backendEndpoint = 'http://localhost:8000/backend';

export default class BoardService {
  socket;
  stompClient;
  connected = false;
  boards;

  constructor() {
    this.socket = new SockJS(backendEndpoint);
    this.stompClient = Stomp.over(this.socket);

    this.boards = [{ id: "TestBoard1" }] //todo: read this from store or something
  }


  connect() {
    this.stompClient.connect(
      {}, //if auth headers are injected here, does that work?
      frame => {
        this.connected = true;
        console.log(frame);
        for (let board in this.boards) {
          console.log(board);
          this.stompClient.subscribe("/boards/TestBoard1", tick => { //url not correct
            console.log(tick);
            console.log(JSON.parse(tick.body).content);
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
}