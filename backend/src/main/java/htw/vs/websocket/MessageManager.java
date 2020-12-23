package htw.vs.websocket;

import htw.vs.data.Board;
import htw.vs.data.Message;
import htw.vs.data.MessageRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * The type Message manager.
 */
@Component
public class MessageManager {
    //TODO: Conurrency???
    private final MessageRepository messageRepository;

    private Map<Board, List<Message>> boardMessages;

    private MessageManager(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        generateBoardMessages(); //ConcurrentHashMap<>(); ???
    }

    private void generateBoardMessages(){
        List<Message> msgs = messageRepository.findMessagesToDisplay();
        boardMessages = msgs.stream().collect(Collectors.groupingBy(m -> m.getBoard()));
        ConcurrentHashMap<Board, CopyOnWriteArrayList<Message>> test
                = msgs.parallelStream().collect(Collector.of(
                        ConcurrentHashMap::new,
                (map, m) -> map.computeIfAbsent(m.getBoard(), k -> {
                    CopyOnWriteArrayList l = new CopyOnWriteArrayList<>();
                    l.add(m);
                    return l;
                }),
                (a, b) -> {
                            b.forEach(( key, set ) -> a.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).addAll(set));
                    return a;
                }
                ));
    }


    public List<Message> addOrReplace(Message msg) {
        msg = messageRepository.save(msg);
        List<Message> msgs = boardMessages.get(msg.getBoard());
        if(msgs == null) {
            msgs = new ArrayList<>(); //CopyOnWriteArrayList<>(); ???
            boardMessages.put(msg.getBoard(), msgs);
        }
        Message finalMsg = msg;
        msgs.removeIf(m -> m.getId() == finalMsg.getId());
        if(finalMsg.isActive())
            msgs.add(finalMsg);
        return msgs;
    }

    public List<Message> getAllByBoard(Board board) {
        return boardMessages.get(board);
    }

    /**public List<Message> remove(Message msg) {
        msg.setActive(false);
        messageRepository.save(msg);
        List<Message> msgs = boardMessages.get(msg.getBoard());
        if(msgs == null) {
            msgs = new ArrayList<>(); //CopyOnWriteArrayList<>(); ???
            boardMessages.put(msg.getBoard(), msgs);
        }
        Message finalMsg = msg;
        msgs.removeIf(m -> m.getId() == finalMsg.getId());
        msgs.add(finalMsg);
        return msgs;
    }**/
}
