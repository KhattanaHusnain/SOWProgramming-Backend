package com.user.management.service;

import com.user.management.dto.ProfileRequest;
import com.user.management.dto.ProfileResponse;
import com.user.management.entity.Profile;
import com.user.management.entity.User;
import com.user.management.exception.ConflictException;
import com.user.management.exception.ResourceNotFoundException;
import com.user.management.repository.ProfileRepository;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger log =
            LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileResponse createProfile(Long userId, ProfileRequest request) {

        log.debug("Creating profile for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found for profile creation: {}", userId);
                    return new ResourceNotFoundException("User", userId);
                });

        if (profileRepository.existsById(userId)) {
            log.warn("Profile already exists for userId {}", userId);
            throw new ConflictException("Profile already exists for this user");
        }

        Profile profile = Profile.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .user(user)
                .build();

        Profile saved = profileRepository.save(profile);

        log.info("Profile created successfully for userId {}", userId);

        return toResponse(saved);
    }

    public ProfileResponse getProfile(Long userId) {

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", userId));

        return toResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long profileId, ProfileRequest request) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", profileId));

        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setPhone(request.phone());

        log.info("Profile updated successfully for profileId {}", profileId);

        return toResponse(profile);
    }

    public void deleteProfile(Long profileId) {

        if (!profileRepository.existsById(profileId)) {
            log.warn("Profile not found for deletion: {}", profileId);
            throw new ResourceNotFoundException("Profile", profileId);
        }

        profileRepository.deleteById(profileId);
        log.info("Profile deleted successfully for profileId {}", profileId);
    }

    private ProfileResponse toResponse(Profile profile) {
        User user = profile.getUser();

        return new ProfileResponse(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhone(),
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
