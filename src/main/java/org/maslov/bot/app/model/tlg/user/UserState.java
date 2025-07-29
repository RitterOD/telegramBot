package org.maslov.bot.app.model.tlg.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.maslov.bot.app.games.model.Activity;

import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = UserState.TABLE_NAME)
public class UserState {

    public static final String TABLE_NAME = "telegram_user_state";
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_status")
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "activity", columnDefinition = "jsonb")
    private Activity activity;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private TelegramUser user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TelegramUser getUser() {
        return user;
    }

    public void setUser(TelegramUser user) {
        this.user = user;
    }
}
