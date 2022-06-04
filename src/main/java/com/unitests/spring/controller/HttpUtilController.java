package com.unitests.spring.controller;

import com.unitests.spring.dto.APIResponse;
import com.unitests.spring.dto.AnythingReq;
import com.unitests.spring.service.AnythingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HttpUtilController {

    private final AnythingService anythingService;

    @PostMapping("/http/anything")
    public APIResponse anything(@RequestBody @Valid AnythingReq request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return APIResponse.error("400", bindingResult.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        }
        return APIResponse.ok("Success");
    }


    @PostMapping("/http/anythingWithCallingService")
    public APIResponse anythingWithCallingService(@RequestBody @Valid AnythingReq request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return APIResponse.error("400", bindingResult.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        }

        return APIResponse.ok(this.anythingService.getAnythingWithDb(request));
    }

}
