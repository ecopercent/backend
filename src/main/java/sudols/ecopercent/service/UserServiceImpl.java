package sudols.ecopercent.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public User createUser(CreateUserRequest postUserDto) {
        return userRepository.save(postUserDto.toEntity());
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> updateUser(Long userId, UpdateUserRequest pathUserDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    BeanUtils.copyProperties(pathUserDto, user, "id");
                    return userRepository.save(user);
                });
    }

    public void deleteUser(Long userId) {
        itemRepository.deleteByUser_Id(userId);
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void deleteAllUser() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}
