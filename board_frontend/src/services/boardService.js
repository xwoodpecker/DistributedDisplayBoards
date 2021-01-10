import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
//import {store} from '../main';

//var getUrl = window.location;
//var baseUrl = getUrl.protocol + "//" + getUrl.host;

const backendEndpoint = 'http://localhost:8000/backend';


let socket = new SockJS(backendEndpoint);
let stompClient = Stomp.over(socket);

let connected = false; //todo: put in store
let boards = [{ id: "TestBoard1" }] //todo: read this from store

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
                console.log(board);
                stompClient.subscribe("/boards/TestBoard1", tick => { //url not correct
                    console.log(tick);
                    console.log(JSON.parse(tick.body).content);
                  });
                send();
            }
        },
        error => {
          console.log(error);
          connected = false;
        }
      );
}

export function send(){
    if (stompClient && stompClient.connected) {
        const msg = {
            content: "content",
            user: 1,
            board: 1,
            ttl: 1234,
            active: true
        }
        console.log(JSON.stringify(msg));
        stompClient.send("/app/huso/message", JSON.stringify(msg));
    }
}

export function disconnect() {
    if (stompClient) {
        stompClient.disconnect();
      }
      connected = false;
}
  