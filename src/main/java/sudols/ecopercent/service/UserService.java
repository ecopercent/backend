package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.UserPatchDto;
import sudols.ecopercent.dto.UserPostDto;
import sudols.ecopercent.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(UserPostDto user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(UserPostDto user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 유저입니다.");
                });
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public void updateProfile(Long userId, UserPatchDto newUserData) {
        userRepository.update(userId, newUserData);
    }

    public void deleteOne(Long userId) {
        userRepository.delete(userId);
    }
}
