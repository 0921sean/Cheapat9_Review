package powersell.cheapat9.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequestDto {
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long itemId;

    @NotNull(message = "주문 수량은 필수입니다.")
    private int count;  // 주문량

    @NotEmpty(message = "이름 입력은 필수입니다.")
    private String name;

    @NotEmpty(message = "전화번호 입력은 필수입니다.")
    private String number;

    @NotEmpty(message = "우편번호 입력은 필수입니다.")
    private String zipcode;

    @NotEmpty(message = "주소 입력은 필수입니다.")
    private String address;

    @NotEmpty(message = "동호수 입력은 필수입니다.")
    private String dongho;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    private String pw;
}
