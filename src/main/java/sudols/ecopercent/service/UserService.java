package sudols.ecopercent.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.RequestPatchUserProfileDto;
import sudols.ecopercent.dto.user.RequestPostUserProfileDto;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public UserService(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public User join(RequestPostUserProfileDto postUserDto) {
        return userRepository.save(postUserDto.toEntity());
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> updateProfile(Long userId, RequestPatchUserProfileDto pathUserDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    BeanUtils.copyProperties(pathUserDto, user, "id");
                    return userRepository.save(user);
                });
    }

    public void deleteOne(Long userId) {
        itemRepository.deleteByUser_Id(userId);
        userRepository.deleteById(userId);
    }

    // Test
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Test
    public void deleteAll() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}
