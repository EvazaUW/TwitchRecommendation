package com.evaza.etwitch.favorite;

import com.evaza.etwitch.db.entity.UserEntity;
import com.evaza.etwitch.model.FavoriteRequestBody;
import com.evaza.etwitch.model.TypeGroupedItemList;
import com.evaza.etwitch.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


// annotation 的路径写在class上，等价于写在下面每个annotation上（每个需要写一样）
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;

//    // Hard-coded user for temporary use, will be replaced in future
//    private final UserEntity userEntity = new UserEntity(1L, "user0", "Foo", "Bar", "password");

    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @GetMapping
    public TypeGroupedItemList getFavoriteItems(@AuthenticationPrincipal User user) {
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        return favoriteService.getGroupedFavoriteItems(userEntity);
    }

    @PostMapping
    public void setFavoriteItem(@AuthenticationPrincipal User user, @RequestBody FavoriteRequestBody body) {
        // if you don't use try...catch, but use throws on the method declaration,
        // spring boot won't know how to deal with that error, and client would 500 level error
        // which means your coding has bug... but is you use the try...catch, then such error would
        // return 400, since spring boot knows that you caught this error
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        try {
            favoriteService.setFavoriteItem(userEntity, body.favorite());
        } catch (DuplicateFavoriteException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate entry for favorite record", e);
        }
        // when spring caught this exception, itself doesn't return "Duplicate entry for favorite record" message now.
        // you need to add another class (TwitchErrorResponse and GlobalControllerExceptionHandler) to handle this.
    }

    @DeleteMapping
    public void unsetFavoriteItem(@AuthenticationPrincipal User user, @RequestBody FavoriteRequestBody body) {
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
    }
}
