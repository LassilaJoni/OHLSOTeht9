package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TilaustenKäsittelyTest {

    @Mock
    IHinnoittelija hinnoittelijaMock;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testaaKäsittelijäWithMockitoHinnoittelija() {
        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 30.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        // Record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus);
        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote);
    }

    @Test
    public void testKäsittelijäHintaYli100() {
        float alkuSaldo = 100.0f;
        float listaHinta = 101.0f;
        float alennus = 20.0f;
        float odotettuAlennus = alennus + 5; // +5% sillä hinta yli 100
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - odotettuAlennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("tuote1", listaHinta);
        // Record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus)
                .thenReturn(odotettuAlennus);
        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote); //Varmistetaan, että on alennusprosentin kutsua on kutsuttu 2kertaa
        verify(hinnoittelijaMock).setAlennusProsentti(asiakas, odotettuAlennus); // Varmistetaan että odotettualennus on oikein
    }

    @Test
    public void testKäsittelijäHinta100() {
        float alkuSaldo = 100.0f;
        float listaHinta = 100.0f;
        float alennus = 20.0f;
        float odotettuAlennus = alennus + 5; // +5% sillä hinta 100
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - odotettuAlennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("tuote1", listaHinta);
        // Record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus)
                .thenReturn(odotettuAlennus);
        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote); //Varmistetaan, että on alennusprosentin kutsua on kutsuttu 2kertaa
        verify(hinnoittelijaMock).setAlennusProsentti(asiakas, odotettuAlennus); // Varmistetaan että odotettualennus on oikein

    }
}
