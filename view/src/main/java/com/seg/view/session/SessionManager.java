package com.seg.view.session;

import com.seg.domain.document.fx.DocumentResponseFx;
import com.seg.domain.enumeration.Role;
import com.seg.domain.user.blueprint.IUserResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class SessionManager {
    
    private static final String INSTANCE_ERROR = "You must call 'create' first before using the SessionManager instance";
    private static SessionManager instance;

    private final IUserResponse user;
    private DocumentResponseFx tableDocument;
    private Role role;

    public static SessionManager create(final IUserResponse user) {
        if (instance == null) instance = new SessionManager(user);
        return instance;
    }

    public static SessionManager instance() throws NullPointerException{
        if (instance == null) throw new NullPointerException(INSTANCE_ERROR);
        else return instance;
    }
}
