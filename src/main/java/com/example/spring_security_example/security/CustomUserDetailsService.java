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

package com.example.spring_security_example.security;

import com.example.spring_security_example.model.User;
import com.example.spring_security_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        CustomUserDetails userDetails;
        if (user != null) {
            userDetails = new CustomUserDetails();
            userDetails.setUser(user);
        } else {
            throw new UsernameNotFoundException("User not found with name : " + username);
        }
        return userDetails;
    }

}
