package com.uevitondev.cashcardsa.cashcard;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(100L, 120.00,"sarah1"),
                new CashCard(101L, 1.00, "sarah1"),
                new CashCard(102L, 150.00, "sarah1"));
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(100L, 120.00, "sarah1");
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(100);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(120.00);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":100,
                    "amount":120.00,
                    "owner":"sarah1"
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(100L, 120.00, "sarah1"));
        assertThat(json.parseObject(expected).id()).isEqualTo(100);
        assertThat(json.parseObject(expected).amount()).isEqualTo(120.00);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {

        String expected = """
                [
                   { "id": 100, "amount": 120.00, "owner":"sarah1" },
                   { "id": 101, "amount": 1.00, "owner":"sarah1" },
                   { "id": 102, "amount": 150.00, "owner":"sarah1" }
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

}
