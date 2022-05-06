/*
 *  ***********************************************************************************
 *                            Copyright (c) AussieDev81 2022                          *
 *  ***********************************************************************************
 *                                     MIT License                                    *
 *                                                                                    *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy    *
 *   of this software and associated documentation files (the "Software"), to deal    *
 *   in the Software without restriction, including without limitation the rights     *
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 *   copies of the Software, and to permit persons to whom the Software is            *
 *   furnished to do so, subject to the following conditions:                         *
 *                                                                                    *
 *   The above copyright notice and this permission notice shall be included in all   *
 *   copies or substantial portions of the Software.                                  *
 *                                                                                    *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR       *
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,         *
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE      *
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER           *
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,    *
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE    *
 *   SOFTWARE.                                                                        *
 *  ***********************************************************************************
 */

package com.example.spring_security_example.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "credentials_non_expired")
    public boolean credentialsNonExpired;

    @Column(name = "account_non_locked")
    public boolean accountNonLocked;

    @Column(name = "account_non_expired")
    public boolean accountNonExpired;

    @Column(name = "active")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private Set<Role> roles;

}
