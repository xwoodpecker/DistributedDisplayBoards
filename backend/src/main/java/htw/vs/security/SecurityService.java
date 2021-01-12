package htw.vs.security;

import htw.vs.data.Group;
import htw.vs.data.GroupRepository;
import htw.vs.data.User;
import htw.vs.data.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("securityService")
public class SecurityService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public SecurityService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public boolean hasPermission(Authentication authentication, Long groupId) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        String currentUserName = authentication.getName();
        User user = userRepository.findUserByUserName(currentUserName);

        Optional<Group> opt = groupRepository.findById(groupId);
        Group group;

        if(user.getRoles().contains("SUPERVISOR"))
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
}