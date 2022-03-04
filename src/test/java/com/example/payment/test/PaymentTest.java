package com.example.payment.test;

import com.example.payment.dao.UserRepository;
import com.example.payment.model.User;
import com.example.payment.model.Wallet;
import com.example.payment.tools.Random10Digit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    void testLengthOfWalletNumber() {
        String walletNumber = Random10Digit.create10Digit();
        assert walletNumber.length() == 10;
    }

    @Test
    void testBuyWithNotEnoughBalance() throws Exception {
        String id = "id";
        User user = new User(id, null, new Wallet(Random10Digit.create10Digit(), 1000));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        mockMvc.perform(put("/payment/user/" + id).param("amount", "10000"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testSuccessfullyBuyStatus() throws Exception {
        String id = "id";
        User user = new User(id, new ArrayList<>(), new Wallet(Random10Digit.create10Digit(), 1000));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        mockMvc.perform(put("/payment/user/" + id).param("amount", "1000"))
                .andExpect(status().isOk());
    }

    @Test
    void testBalanceWithSuccessfullyBuy() throws Exception {
        String id = "id";
        User user = new User(id, new ArrayList<>(), new Wallet(Random10Digit.create10Digit(), 1000));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        mockMvc.perform(put("/payment/user/" + id).param("amount", "1000")).andReturn();
        //1000-1000 = 0
        assert user.getWallet().getAccountBalance()==0;

    }

    @Test
    void testChargeWalletWithZeroAmount() throws Exception {
        when(userRepository.findById("id")).thenReturn(Optional.of(new User()));
        mockMvc.perform(put("/payment/user/id/charge?amount=0")).andExpect(status().isUnauthorized());
    }

    @Test
    void testChargeWalletWithMinesAmount() throws Exception {
        when(userRepository.findById("id")).thenReturn(Optional.of(new User()));
        mockMvc.perform(put("/payment/user/id/charge?amount=-100")).andExpect(status().isUnauthorized());
    }

    @Test
    void testSuccessfullyChargeWalletStatusOk() throws Exception {
        User user = new User("id", new ArrayList<>(), new Wallet(Random10Digit.create10Digit(), 0));
        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        mockMvc.perform(put("/payment/user/id/charge?amount=1000")).andExpect(status().isOk());
    }

    @Test
    void testSuccessfullyChargeWalletBody() throws Exception {
        int amount = 1000;
        User user = new User("id", new ArrayList<>(), new Wallet(Random10Digit.create10Digit(), 0));
        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        mockMvc.perform(put("/payment/user/id/charge?amount=" + amount))
                .andExpect(content().string(amount + ""));
    }
}
