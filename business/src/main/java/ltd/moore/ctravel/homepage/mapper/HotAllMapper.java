package ltd.moore.ctravel.homepage.mapper;

import ltd.moore.ctravel.experience.model.ExperienceDetailVO;
import ltd.moore.ctravel.homepage.model.HomePageHotVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cocouzx on 2017-6-26 0026.
 */
@Repository
@Component
public interface HotAllMapper {
    /**
     * 获取热门类型
     */
    List<HomePageHotVO> getHotService();

    /**
     * 获取热门目的地
     */
    List<HomePageHotVO> getHotDestinations();

    /**
     * 获取热门体验
     * @return
     */
    List<ExperienceDetailVO> getHotExperience();

    /**
     * 获取非热门体验
     * @return
     */
    List<ExperienceDetailVO> getNotHotExperience();
    /**
     * 子页面，添加热门体验
     * @param idss
     * @param id
     */
    void addHotExperience(String idss, String id);

    /**
     * 删除热门体验
     * @param idss
     */
    void deleteHotExperienct(String idss);
    /**
     * 获取非热门目的地
     * @return
     */
    List<HomePageHotVO>  getNotHotDestinations();
    /**
     * 增加热门目的地
     */
    void addHotDestination(String s, String id);
    /**
     * 删除热门目的地
     */
    void deleteHotDestination(String idss);
    void deleteHotDestinationRelative(String idss);
    /**
     * 获取非热门服务
     * @return
     */
    List<HomePageHotVO> getNotHotService();
    /**
     * 删除热门服务
     */
    void deleteHotService(String idss);
    void deleteHotServiceRelative(String idss);
    /**
     * 增加热门服务
     */
    void addHotService(String id, String ids);
    /**
     * 非热门目的地的体验
     * @param hotDestination
     * @return
     */
    List<ExperienceDetailVO> getNotHotDestinationsExperience(String hotDestination);
    /**
     * 获取热门目的地体验
     * @param hotDestination
     * @return
     */
    List<ExperienceDetailVO> getHotDestinationExperience(String hotDestination);

    /**
     * 通过ID获取热门目的地信息
     * @param hotDestination
     * @return
     */
    String getHotDestinationById(String hotDestination);
    /**
     * 通过ID获取热门服务信息
     * @param hotService
     * @return
     */
    String getHotServiceById(String hotService);
    /**
     * 获取热门服务地体验
     * @param temp
     * @return
     */
    List<ExperienceDetailVO> getHotServiceExperienct(String temp);
    /**
     * 添加热门目的地体验
     */
    void addHotDestinationExperience(String idss1, String s, String idss);

    /**
     * 删除热门目的地体验
     * @param idss
     */
    void deleteHotDestinationExperience(String idss);
    /**
     * 删除热门服务体验
     * @param idss
     */
    void deleteHotServiceExperience(String idss);
    /**
     * 非热门服务的体验
     */
    List<ExperienceDetailVO> getNotHotServiceExperience(String hotDestination);
    /**
     * 添加热门服务体验
     */
    void addHotServiceExperience(String idss, String id, String hotService);



}
