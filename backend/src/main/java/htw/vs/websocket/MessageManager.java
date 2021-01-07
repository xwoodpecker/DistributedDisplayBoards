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
    private final MessageRepository messageRepository;

    private Map<Board, List<Message>> boardMessages;

    private MessageManager(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        generateBoardMessages();
    }

    private void generateBoardMessages(){
        List<Message> msgs = messageRepository.findMessagesToDisplay();
        boardMessages = msgs.parallelStream().collect(Collector.of(
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
        //msgs.stream().collect(Collectors.groupingBy(m -> m.getBoard()));
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
}
