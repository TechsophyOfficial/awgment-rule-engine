package com.techsophy.tsf.rule.engine.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ViewControllerImpl
{
    public String viewIndexPage()
    {
        return INDEX_URL;
    }

    public String viewModelerPage(Model model,Long id)
    {
		model.addAttribute(SAVE_DRD_REQUESTPARAM_ID,id);
        return MODELER_URL;
    }
}
