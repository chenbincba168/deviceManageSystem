package cn.tycoding.controller;

import cn.tycoding.pojo.Device;
import cn.tycoding.pojo.PageBean;
import cn.tycoding.service.DeviceService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * 这是客户管理的Controller层
 *
 * @author tycoding
 * @date 21-5-15下午7:26
 */
@Controller
@RequestMapping(value = "/device")
public class DeviceController {

    /**
     * 注入service层
     * 使用@Resource和@Autowired都可以实现Bean的自动注入
     */
    @Autowired
    private DeviceService deviceService;

    /**
     * 跳转到添加客户功能页面
     */
    @RequestMapping("/toAddDevice")
    public String toSavePage() {

        return "page/addDevice";
    }

    /**
     * 跳转到客户列表页面e
     */
    @RequestMapping(value = "/toShowDeviceList")
    public String toListPage(Model model) {
        return "redirect:findByPage";
    }

    /**
     * 客户信息保存
     *
     * @param device
     * @param model
     * @return
     */
    @RequestMapping(value = "/save")
    public String create(Device device, Model model) {
        try {
            deviceService.create(device);
            model.addAttribute("message", "保存设备成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "保存设备失败");
        }
        return "page/otherInfo";
    }

    /**
     * 客户信息删除的方法
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam Long id, Model model) {
        try {
            deviceService.delete(id);
            model.addAttribute("message", "删除客户信息成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "page/otherInfo";
    }


    /**
     * 根据id查询客户信息方法
     *
     * @param device
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findById")
    public Device findById(@RequestBody Device device) {
        Device device_info = deviceService.findById(device.getId());
        if (device_info != null) {
            device_info.setDeviceProperty("123");//手动修改参数
            return device_info;
        } else {
            return null;
        }
    }

    /**
     * 更新客户信息的方法
     *
     * @param device
     * @param model
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(Device device, Model model) {
        try {
            deviceService.update(device);
            return "redirect:findByPage";//修改成功后直接返回查询界面
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "更新设备信息失败");
            return "page/otherInfo";
        }
    }

    /**
     * 分页查询
     *
     * @param device 查询条件
     * @param pageCode 当前页
     * @param pageSize 每页显示的记录数
     * @return
     */
    @RequestMapping("/findByPage")
    public String findByPage(Device device,
                             @RequestParam(value = "pageCode", required = false, defaultValue = "1") int pageCode,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
                             Model model) {
        // 回显数据
        Object obj = deviceService.findByPage(device, pageCode, pageSize);
        model.addAttribute("page", obj);

        String connectStatus = "连接成功";
        model.addAttribute("connect", connectStatus);
        return "page/queryDevice";
    }
}
