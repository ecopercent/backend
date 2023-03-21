package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UserProfilePatchDto;
import sudols.ecopercent.dto.user.UserProfilePostDto;
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

    public Long join(UserProfilePostDto user) {
        validateDuplicateUser(user);
        User userEntity = user.toEntity();
        return userRepository.save(userEntity).getUserId();
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public void updateProfile(Long userId, UserProfilePatchDto newUserData) {
        validateExistUserById(userId);
        userRepository.updateProfile(userId, newUserData.toEntity());
    }

    public void deleteOne(Long userId) {
        validateExistUserById(userId);
        userRepository.deleteById(userId);
    }

    // Test
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Test
    public void deleteAll() {
        userRepository.clearStore();
    }

    private void validateDuplicateUser(UserProfilePostDto user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 유저입니다.");
                });
    }

    private void validateExistUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent() == false) {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }
    }
}
