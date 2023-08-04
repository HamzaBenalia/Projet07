package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.forms.BidListForm;
import com.openclassrooms.poseidon.services.BidListService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BidListControllerTest {


    @InjectMocks
    private BidListController controller;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private BidListService bidListService;

    @Mock
    private Model model;

    @Mock
    private HttpSession httpSession;

    @Mock
    private BindingResult result;

    @Test
    public void testBidListHomePage() {
        String view = controller.bidListHomePage(model);
        assertEquals("redirect:/", view);
    }

    @Test
    public void testViewHomePage() {
        // Setup request and session
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        HttpSession session = request.getSession();

        Authentication auth = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String view = controller.viewHomePage(model);

        // Verify operations
        verify(model).addAttribute(eq("bidListList"), any(List.class));
        assertEquals("index", view);
    }

    @Test
    public void testShowNewBidListForm() {
        String view = controller.showNewBidListForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(BidListForm.class));
        assertEquals("newBidList", view);
    }

    @Test
    public void testSaveBidList() {
        BidListForm form = new BidListForm();
        form.setBidListId("18");
        form.setBidQuantity("200");
        when(result.hasErrors()).thenReturn(false);
        String view = controller.saveBidList(form, result, model);
        verify(bidListService, times(1)).saveBidList(any(BidList.class));
        assertEquals("redirect:/bidListHomePage", view);
    }

    @Test
    public void testSaveBidList_WithErrors() {
        BidListForm form = new BidListForm();
        when(result.hasErrors()).thenReturn(true);
        String view = controller.saveBidList(form, result, model);
        verify(bidListService, times(0)).saveBidList(any(BidList.class));
        assertEquals("newBidList", view);
    }

    @Test
    public void showFormForBidListUpdate_whenBidListPresent_returnsUpdateBidListView() {
        // Given
        when(bidListService.findById(anyLong())).thenReturn(Optional.of(new BidList()));

        // When
        String viewName = controller.showFormForBidListUpdate(1L, model);

        // Then
        assertEquals("updateBidList", viewName);
    }

    @Test
    public void showFormForBidListUpdate_whenBidListAbsent_returnsRedirectBidListHomePageView() {
        // Given
        when(bidListService.findById(anyLong())).thenReturn(Optional.empty());

        // When
        String viewName = controller.showFormForBidListUpdate(1L, model);

        // Then
        assertEquals("redirect:/bidListHomePage", viewName);
    }


    @Test
    public void testSaveBidListWithException() {
        BidListForm bidListForm = new BidListForm();
        // set form fields here

        when(result.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(bidListService).saveBidList(any(BidList.class));

        String view = controller.saveBidList(bidListForm, result, model);

        assertEquals("newBidList", view);
        verify(result).rejectValue(eq("bidQuantity"), eq(""), anyString());
    }

    @Test
    public void updateBidList_whenFormHasErrors_returnsUpdateBidListView() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = controller.updateBidList(1L, new BidListForm(), bindingResult);

        // Then
        assertEquals("updateBidList", viewName);
    }

//    @Test
//    public void updateBidList_whenNoErrorsAndNoException_returnsRedirectBidListHomePageView() {
//        // Given
//
//        BidListForm bidListForm = new BidListForm();
//        bidListForm.setBidQuantity("RuleName1");
//        bidListForm.setBidListId("1");
//        bidListForm.setAccount("20");
//        when(bindingResult.hasErrors()).thenReturn(false);
//        when(bidListService.newBidList(anyLong(), any(BidList.class))).thenReturn(new BidList());
//
//        // When
//        String viewName = controller.updateBidList(1L, new BidListForm(), bindingResult);
//
//        // Then
//        assertEquals("redirect:/bidListHomePage", viewName);
//    }

    @Test
    public void updateBidList_whenNoErrorsAndExceptionOccurs_returnsUpdateBidListView() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bidListService.newBidList(anyLong(), any(BidList.class))).thenThrow(RuntimeException.class);

        // When
        String viewName = controller.updateBidList(1L, new BidListForm(), bindingResult);

        // Then
        assertEquals("updateBidList", viewName);
    }


    @Test
    public void showFormForBidListUpdate_whenExceptionThrown_returnsRedirectBidListHomePageView() {
        // Given
        when(bidListService.findById(anyLong())).thenThrow(RuntimeException.class);

        // When
        String viewName = controller.showFormForBidListUpdate(1L, model);

        // Then
        assertEquals("redirect:/bidListHomePage", viewName);
    }

    @Test
    public void testDeleteBidList() {
        String view = controller.deleteBidList(1L);
        verify(bidListService, times(1)).deleteBidList(1L);
        assertEquals("redirect:/bidListHomePage", view);
    }

}
