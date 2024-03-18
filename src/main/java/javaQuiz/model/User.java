package javaQuiz.model;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name="user")
public class User implements UserDetails{
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String ime;
	private String prezime;
	private String username;
	private String password;
	private String role;
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datum;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public User(int id, String ime, String prezime, String username, String password,String email, Date datum, String role) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.username = username;
		this.password = password;
		this.email = email;
		this.datum = datum;
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", username=" + username
				+ ", password=" + password +", email=" +email + ", datum=" + datum + ", role=" + role + "]";
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ovde možete da vratite listu uloga ili privilegija koje ima ovaj korisnik
        // za sada ćemo vratiti praznu listu
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        // ovde možete da proverite da li je nalog ovog korisnika istekao
        // za sada ćemo vratiti true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // ovde možete da proverite da li je nalog ovog korisnika zaključan
        // za sada ćemo vratiti true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // ovde možete da proverite da li su kredencijali ovog korisnika istekli
        // za sada ćemo vratiti true
        return true;
    }

    @Override
    public boolean isEnabled() {
        // ovde možete da proverite da li je nalog ovog korisnika omogućen
        // za sada ćemo vratiti true
        return true;
    }
}
