package pexper.projects.project_hub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String fullName;
    private String rg;
    private String issuingAuthority;
    private String issueDate;
    private String birthDate;
    private String motherName;
    private String fatherName;
    private String cpf;
    private String birthplace;
    private String birthState;
    private String address;
    private String addressNumber;
    private String addressComplement;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private String username;
    private String password;
    private String permissionLevel;
    private String jobTitle;
    private String cltOrCnpj;
    private String hireDate;
    private String asoExpiry;
    private String nr10Expiry;
    private String nr35Expiry;
    private String status;
    private String note1;
    private String note2;
    private String note3;
}
