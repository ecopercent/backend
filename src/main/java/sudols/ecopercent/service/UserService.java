package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UserProfilePatchDto;
import sudols.ecopercent.dto.user.UserProfilePostDto;
import sudols.ecopercent.dto.user.UserTitleItemPatchDto;
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

    public void updateTitleTumbler(Long userId, UserTitleItemPatchDto newTitleItemData) {
        validateExistUserById(userId);
        Optional<Item> optionalItem = itemRepository.findById(newTitleItemData.getTitleTumblerId());
        if (optionalItem.isPresent() == false) {
            throw new IllegalStateException("해당 아이템이 존재하지 않습니다.");
        }
        if (optionalItem.get().getCategory().equals("tumbler") == false) {
            throw new IllegalStateException("해당 아이템은 텀블러가 아닙니다.");
        }
        userRepository.updateTitleTumbler(userId, newTitleItemData.toEntity());
    }

    public void updateTitleEcobag(Long userId, UserTitleItemPatchDto newTitleItemData) {
        validateExistUserById(userId);
        Optional<Item> optionalItem = itemRepository.findById(newTitleItemData.getTitleEcobagId());
        if (optionalItem.isPresent() == false) {
            throw new IllegalStateException("해당 아이템이 존재하지 않습니다.");
        }
        System.out.println(optionalItem.get().toString());
        if (optionalItem.get().getCategory().equals("ecobag") == false) {
            throw new IllegalStateException("해당 아이템은 에코백이 아닙니다.");
        }
        userRepository.updateTitleEcobag(userId, newTitleItemData.toEntity());
    }

    public Optional<Item> getTitleTumbler(Long userId) {
        // TODO: 고민. 아래 두 코드가 중복되는데 어떻게 처리할지?
        validateExistUserById(userId);
        User user = userRepository.findById(userId).get();
        Optional<Item> optionalItem = itemRepository.findById(user.getTitleTumblerId());
        if (optionalItem.isPresent() == false) {
            throw new IllegalStateException("해당 아이템이 존재하지 않습니다.");
        }
        return optionalItem;
    }

    public Optional<Item> getTitleEcobag(Long userId) {
        // TODO: 고민. 아래 두 코드가 중복되는데 어떻게 처리할지?
        validateExistUserById(userId);
        User user = userRepository.findById(userId).get();
        Optional<Item> optionalItem = itemRepository.findById(user.getTitleEcobagId());
        if (optionalItem.isPresent() == false) {
            throw new IllegalStateException("해당 아이템이 존재하지 않습니다.");
        }
        return optionalItem;
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
