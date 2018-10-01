package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.model.TokenHolder
import com.koonat.ouluhealth.domain.repository.AccessTokenRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class GetAccessTokenInteractorTest {

    companion object {
        val TOKEN_HOLDER = TokenHolder("TOKEN")
    }

    private lateinit var SUT: GetAccessTokenInteractor

    @Mock
    lateinit var accesstokenRepo: AccessTokenRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        SUT = GetAccessTokenInteractor(accesstokenRepo)
    }

    @Test
    fun `execute_endpointGetsTriggered`() {
        // Arrange
        `when`(accesstokenRepo.accessToken).thenReturn(Single.just(TOKEN_HOLDER))
        // Act
        SUT.execute()
        // Assert
        verify(accesstokenRepo).accessToken
    }

    @Test
    fun `execute_success_correctTokenReturned`() {
        // Arrange
        `when`(accesstokenRepo.accessToken).thenReturn(Single.just(TOKEN_HOLDER))
        // Act
        val testObserver = SUT.execute().test()
        // Assert
        testObserver.assertValue(TOKEN_HOLDER)
    }
}