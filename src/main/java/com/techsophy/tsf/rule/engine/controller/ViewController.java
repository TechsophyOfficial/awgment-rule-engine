package com.techsophy.tsf.rule.engine.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;

@RequestMapping(BASE_URL+ VERSION_V1)
@RestController
@Validated
public interface ViewController
{
    @GetMapping({BASE, BASE+INDEX_URL})
    @PreAuthorize(READ_OR_ALL_ACCESS)
    String viewIndexPage(Model model);

    @GetMapping({BASE+MODELER_URL})
    @PreAuthorize(READ_OR_ALL_ACCESS)
    String viewModelerPage(Model model, @RequestParam(value = SAVE_DRD_REQUESTPARAM_ID, required = false) Long id);
}
