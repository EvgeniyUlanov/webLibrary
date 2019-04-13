package ru.otus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.domain.Book;
import ru.otus.services.BookService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {

    @Autowired
    private JdbcMutableAclService aclService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @PostConstruct
    public void setUp() {
//        UserDetails admin = userDetailsService.loadUserByUsername("admin");
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(admin, admin, admin.getAuthorities()));
//        Sid owner = new GrantedAuthoritySid("ROLE_ADMIN");
//        Book book = bookService.getBookById(1L);
//        ObjectIdentity objectIdentity = new ObjectIdentityImpl(book.getClass(), book.getId());
//        MutableAcl acl = aclService.createAcl(objectIdentity);
//        acl.setOwner(owner);
//        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, owner, true);
//        aclService.updateAcl(acl);
    }
}
