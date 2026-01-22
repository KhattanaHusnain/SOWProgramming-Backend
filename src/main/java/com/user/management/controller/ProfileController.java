package com.user.management.controller;

import com.user.management.dto.ProfileRequest;
import com.user.management.dto.ProfileResponse;
import com.user.management.entity.Profile;
import com.user.management.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PostMapping("/{userId}")
    public ResponseEntity<ProfileResponse> createProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileRequest request
    ) {
        ProfileResponse response = service.createProfile(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(service.getProfile(userId));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileRequest request
    ) {
        return ResponseEntity.ok(service.updateProfile(profileId, request));
    }

    @PatchMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> patchProfile(
            @PathVariable Long profileId,
            @RequestBody ProfileRequest request
    ) {
        return ResponseEntity.ok(service.patchProfile(profileId, request));
    }


    @DeleteMapping("/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable Long profileId) {
        service.deleteProfile(profileId);
    }
}
