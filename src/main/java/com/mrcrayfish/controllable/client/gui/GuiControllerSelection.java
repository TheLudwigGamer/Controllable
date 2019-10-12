package com.mrcrayfish.controllable.client.gui;

import com.mrcrayfish.controllable.Controllable;
import com.studiohartman.jamepad.ControllerManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

/**
 * Author: MrCrayfish
 */
public class GuiControllerSelection extends GuiScreen
{
    private int controllerCount;
    private boolean mainMenu;
    private ControllerManager manager;
    private GuiListControllers listControllers;

    private GuiButton btnSettings;
    private GuiButton btnRemap;
    private GuiButton btnBack;

    public GuiControllerSelection(ControllerManager manager, boolean mainMenu)
    {
        this.manager = manager;
        this.mainMenu = mainMenu;
        this.controllerCount = manager.getNumControllers();
    }

    @Override
    public void initGui()
    {
        listControllers = new GuiListControllers(manager, mc, this.width, this.height, 32, this.height - 44, 20);
        this.addButton(btnSettings = new GuiButton(0, this.width / 2 - 154, this.height - 32, 100, 20, I18n.format("controllable.gui.settings"))); //TODO localize I18n.format("selectWorld.select")
        this.addButton(btnRemap = new GuiButton(1, this.width / 2 - 50, this.height - 32, 100, 20, I18n.format("controllable.gui.remap")));
        this.addButton(btnBack = new GuiButton(2, this.width / 2 + 54, this.height - 32, 100, 20, I18n.format("controllable.gui.back")));
        btnRemap.enabled = false;
    }

    @Override
    public void updateScreen()
    {
        if(controllerCount != manager.getNumControllers())
        {
            controllerCount = manager.getNumControllers();
            listControllers.reload();
            listControllers.setSelectedElement(Controllable.getSelectedControllerIndex());
            btnRemap.enabled = listControllers.getSelectedIndex() > -1;
            btnSettings.enabled = listControllers.getSelectedIndex() > -1;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(!button.enabled)
            return;

        if(button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiControllerSettings(this));
        }
        else if(button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiControllerLayout());
        }
        else if(button.id == 2)
        {
            if(mainMenu)
            {
                this.mc.displayGuiScreen(null);
            }
            else
            {
                this.mc.displayGuiScreen(new GuiIngameMenu());
            }
        }
    }



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        listControllers.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRenderer, I18n.format("controllable.gui.title.select_controller"), this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        listControllers.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        listControllers.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        listControllers.mouseReleased(mouseX, mouseY, state);
    }
}
