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

package com.example.spring_security_example.configuration;

import com.example.spring_security_example.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encodePassword());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //Order by the most restrictive to the least restrictive
                //Teacher 'admin' domain
                .antMatchers("/admin/**").hasRole("ADMIN")
                //Student domain
                .antMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                //Home URL (and our H2 database) unrestricted access
                .antMatchers("/", "/h2/**").permitAll()
                //Use the login form
                .and().formLogin()
                //Logout
                .and().logout()
                //Redirect to the homepage on logout
                .logoutSuccessUrl("/")
                //Terminate the session
                .invalidateHttpSession(true)
                //Clear session cookies
                .deleteCookies("JSESSIONID");

        //Disable cross site referencing and header frames for H2 database
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
