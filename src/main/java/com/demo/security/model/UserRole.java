package com.demo.security.model;


import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("ROLES")
public class UserRole {
    @Id // db document field: _key
    private String id;
    @ArangoId // db document field: _id
    private String arangoId;

    private String name;

//    @JsonIgnore
//    @Relations(edges = UserReference.class, lazy = false, maxDepth = 1, direction= Relations.Direction.INBOUND)
//    private Collection<UserInfo> users;

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", arangoId='" + arangoId + '\'' +
                ", name='" + name + '\'' +
//                ", users=" + users +
                '}';
    }

    public UserRole(String Name){
        this.name=Name;
    }
}
