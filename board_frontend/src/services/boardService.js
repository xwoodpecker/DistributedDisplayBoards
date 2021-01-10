import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
//import {store} from '../main';

//var getUrl = window.location;
//var baseUrl = getUrl.protocol + "//" + getUrl.host;

const backendEndpoint = 'endpoint'; //set to rabbitmq url

const socket = new SockJS(backendEndpoint);
const stompClient = Stomp.over(socket);

let connected = false; //todo: put in store
let boards = [{ id: "1234" }] //todo: read this from store

export default {
    connect, disconnect, send, connected
}

export function connect(){
    stompClient.connect(
        {}, //if auth headers are injected here, does that work?
        frame => {
          connected = true;
          console.log(frame);
            for(let board in boards){
                this.stompClient.subscribe("/topic/" + board.id, tick => { //url not correct
                    console.log(tick);
                    console.log(JSON.parse(tick.body).content);
                  });
            }
        },
        error => {
          console.log(error);
          connected = false;
        }
      );
}

export function send(){
    //not implemented
    console.log("Send message:" + this.send_message);
    if (this.stompClient && this.stompClient.connected) {
      const msg = { name: this.send_message };
      console.log(JSON.stringify(msg));
      this.stompClient.send("/app/hello", JSON.stringify(msg), {});
    }
}

export function disconnect() {
    if (stompClient) {
        stompClient.disconnect();
      }
      connected = false;
}
  