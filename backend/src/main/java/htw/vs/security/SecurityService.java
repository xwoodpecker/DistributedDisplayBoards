package htw.vs.security;

import htw.vs.base.Const;
import htw.vs.data.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Security service.
 */
@Component("securityService")
public class SecurityService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private BoardRepository boardRepository;

    /**
     * Instantiates a new Security service.
     *
     * @param groupRepository the group repository
     * @param userRepository  the user repository
     * @param boardRepository the board repository
     */
    public SecurityService(GroupRepository groupRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * Has permission boolean.
     *
     * @param authentication the authentication
     * @param groupId        the group id
     * @return the boolean
     */
    public boolean hasPermissionGroup(Authentication authentication, Long groupId) {

        String currentUserName = authentication.getName();
        User user = userRepository.findUserByUserName(currentUserName);

        Optional<Group> opt = groupRepository.findById(groupId);
        Group group;

        if(user == null){
            return false;
        }

        if(user.getRoles().stream().filter(r -> r.getName().equals(Const.SUPERVISOR_ROLE)).count() > 0)
        {
            return true;
        }
        else if(opt.isPresent()) {
            group = opt.get();
        }else {
            return  false;
        }

        if(group.getCoordinator().getId() == user.getId()) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Has permission board boolean.
     *
     * @param authentication the authentication
     * @param boardId        the board id
     * @return the boolean
     */
    public boolean hasPermissionBoard(Authentication authentication, Long boardId) {

        String currentUserName = authentication.getName();
        User user = userRepository.findUserByUserName(currentUserName);

        Optional<Board> opt = boardRepository.findById(boardId);
        Group group;

        if(user == null)
            return false;

        if(user.getRoles().stream().filter(r -> r.getName().equals(Const.SUPERVISOR_ROLE)).count() > 0)
        {
            return true;
        }
        else if(opt.isPresent()) {
            group = opt.get().getGroup();
        }else {
            return  false;
        }

        if(group.getCoordinator().getId() == user.getId()) {
            return true;
        }else{
            return false;
        }
    }

    public boolean hasPermissionUser(Authentication authentication, Long userId) {

        String currentUserName = authentication.getName();
        User authenticatedUser = userRepository.findUserByUserName(currentUserName);
        Optional<User> user = userRepository.findById(userId);

        if(authenticatedUser == null)
            return false;

        if(authenticatedUser.getRoles().stream().filter(r -> r.getName().equals(Const.SUPERVISOR_ROLE)).count() > 0)
            return true;

        if(!user.isPresent())
            return false;


        return authenticatedUser.getId().equals(userId);

    }
}