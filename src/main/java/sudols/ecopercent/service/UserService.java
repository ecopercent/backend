package sudols.ecopercent.service;

import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UserProfilePatchDto;
import sudols.ecopercent.dto.user.UserProfilePostDto;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
//@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(UserProfilePostDto user) {
        User userEntity = user.toEntity();
        return userRepository.save(userEntity).getUserId();
    }

    private void validateDuplicateUser(UserProfilePostDto user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 유저입니다.");
                });
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public void updateProfile(Long userId, UserProfilePatchDto newUserData) {
        userRepository.update(userId, newUserData.toEntity());
    }

    public void deleteOne(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAll() {
        userRepository.clearStore();
    }
}
