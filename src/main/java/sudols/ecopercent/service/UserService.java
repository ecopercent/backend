package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.UserPatchDto;
import sudols.ecopercent.dto.UserPostDto;
import sudols.ecopercent.repository.UserRepository;

import java.util.Optional;

@Transactional
//@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(UserPostDto user) {
        User userEntity = user.toEntity();
        return userRepository.save(userEntity).getUserId();
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
        userRepository.update(userId, newUserData.toEntity());
    }

    public void deleteOne(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAll() {
        userRepository.clearStore();
    }
}
