package com.accountsmanager.service.transfer;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferDto {
    private final Long userReceiveId;
    private final Long accountReceiveId;
    private final BigDecimal amount;
    private final String accountNumber;

    private TransferDto(final BuilderTransferDto builderTransferDto) {
        this.userReceiveId = builderTransferDto.userReceiveId;
        this.accountReceiveId = builderTransferDto.accountReceiveId;
        this.amount = builderTransferDto.amount;
        this.accountNumber = builderTransferDto.accountNumber;
    }

    public BuilderTransferDto toBuilder(){
        return new BuilderTransferDto()
                .withUserReceiveId(userReceiveId)
                .withAccountReceiveId(accountReceiveId)
                .withAmount(amount)
                .withAccountNumber(accountNumber);
    }
    public static BuilderTransferDto builder(){
        return new BuilderTransferDto();
    }

    public static class BuilderTransferDto {
        private Long userReceiveId;
        private Long accountReceiveId;
        private BigDecimal amount;
        private String accountNumber;

        private BuilderTransferDto() {
        }

        public TransferDto build(){
            return new TransferDto(this);
        }

        public BuilderTransferDto withUserReceiveId(Long userReceiveId) {
            this.userReceiveId = userReceiveId;
            return this;
        }

        public BuilderTransferDto withAccountReceiveId(Long accountReceiveId) {
            this.accountReceiveId = accountReceiveId;
            return this;
        }

        public BuilderTransferDto withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public BuilderTransferDto withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
    }
}
