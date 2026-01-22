package com.user.management.service;

import com.user.management.dto.ProfileRequest;
import com.user.management.dto.ProfileResponse;
import com.user.management.entity.Profile;
import com.user.management.entity.Role;
import com.user.management.entity.SkillLevel;
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

    /* ------------------ Create Profile ------------------ */

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
                .bio(request.bio())
                .profileImage(request.profileImage())
                .role(Role.valueOf(request.role()))
                .skillLevel(
                        request.skillLevel() != null
                                ? SkillLevel.valueOf(request.skillLevel())
                                : null
                )
                .interests(request.interests())
                .githubUrl(request.githubUrl())
                .linkedinUrl(request.linkedinUrl())
                .websiteUrl(request.websiteUrl())
                .preferredLanguage(request.preferredLanguage())
                .timezone(request.timezone())
                .totalCoursesEnrolled(0)
                .totalCoursesCompleted(0)
                .user(user)
                .build();

        Profile saved = profileRepository.save(profile);

        log.info("Profile created successfully for userId {}", userId);

        return toResponse(saved);
    }

    /* ------------------ Get Profile ------------------ */

    public ProfileResponse getProfile(Long userId) {

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", userId));

        return toResponse(profile);
    }

    /* ------------------ Update Profile ------------------ */

    @Transactional
    public ProfileResponse patchProfile(Long profileId, ProfileRequest request) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", profileId));

        if (request.firstName() != null)
            profile.setFirstName(request.firstName());

        if (request.lastName() != null)
            profile.setLastName(request.lastName());

        if (request.phone() != null)
            profile.setPhone(request.phone());

        if (request.profileImage() != null)
            profile.setProfileImage(request.profileImage());

        if (request.bio() != null)
            profile.setBio(request.bio());

        if (request.skillLevel() != null)
            profile.setSkillLevel(SkillLevel.valueOf(request.skillLevel()));

        if (request.interests() != null)
            profile.setInterests(request.interests());

        if (request.githubUrl() != null)
            profile.setGithubUrl(request.githubUrl());

        if (request.linkedinUrl() != null)
            profile.setLinkedinUrl(request.linkedinUrl());

        if (request.websiteUrl() != null)
            profile.setWebsiteUrl(request.websiteUrl());

        if (request.preferredLanguage() != null)
            profile.setPreferredLanguage(request.preferredLanguage());

        if (request.timezone() != null)
            profile.setTimezone(request.timezone());

        log.info("Profile partially updated for profileId {}", profileId);

        return toResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long profileId, ProfileRequest request) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", profileId));

        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setPhone(request.phone());
        profile.setBio(request.bio());
        profile.setProfileImage(request.profileImage());
        profile.setInterests(request.interests());
        profile.setGithubUrl(request.githubUrl());
        profile.setLinkedinUrl(request.linkedinUrl());
        profile.setWebsiteUrl(request.websiteUrl());
        profile.setPreferredLanguage(request.preferredLanguage());
        profile.setTimezone(request.timezone());

        if (request.skillLevel() != null) {
            profile.setSkillLevel(SkillLevel.valueOf(request.skillLevel()));
        }

        log.info("Profile updated successfully for profileId {}", profileId);

        return toResponse(profile);
    }

    /* ------------------ Delete Profile ------------------ */

    public void deleteProfile(Long profileId) {

        if (!profileRepository.existsById(profileId)) {
            log.warn("Profile not found for deletion: {}", profileId);
            throw new ResourceNotFoundException("Profile", profileId);
        }

        profileRepository.deleteById(profileId);
        log.info("Profile deleted successfully for profileId {}", profileId);
    }

    /* ------------------ Mapper ------------------ */

    private ProfileResponse toResponse(Profile profile) {

        User user = profile.getUser();

        return new ProfileResponse(
                profile.getId(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),

                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhone(),
                profile.getProfileImage(),
                profile.getBio(),

                profile.getRole().name(),
                profile.getSkillLevel() != null ? profile.getSkillLevel().name() : null,
                profile.getInterests(),

                profile.getTotalCoursesEnrolled(),
                profile.getTotalCoursesCompleted(),

                profile.getGithubUrl(),
                profile.getLinkedinUrl(),
                profile.getWebsiteUrl(),

                profile.getPreferredLanguage(),
                profile.getTimezone()
        );
    }
}
