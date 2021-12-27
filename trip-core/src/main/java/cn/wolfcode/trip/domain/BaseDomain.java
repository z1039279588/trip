package cn.wolfcode.trip.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDomain {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
}
