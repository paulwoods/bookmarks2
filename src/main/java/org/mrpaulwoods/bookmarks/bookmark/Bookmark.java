package org.mrpaulwoods.bookmarks.bookmark;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Setter
@Getter
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 200)
    @NotNull
    @Size(min = 1, max = 200, message = "The name must be between 1 and 200 characters.")
    private String name;

    @Column(name = "url", nullable = false, length = 1000)
    @NotNull
    @Size(min = 1, max = 1000, message = "The name must be between 1 and 1000 characters.")
    private String url;

    public Bookmark() {
    }

    public Bookmark(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Bookmark bookmark = (Bookmark) o;
        return getId() != null && Objects.equals(getId(), bookmark.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "url = " + url + ")";
    }
}
