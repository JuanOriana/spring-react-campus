import {
  FormArea,
  FormButton,
  FormInput,
  FormWrapper,
  FormLabel,
  ErrorMessage,
} from "../../../components/generalStyles/form";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import { useForm } from "react-hook-form";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

const toUser = {
  name: "Juan",
  surname: "Doe",
  userId: 1,
  username: "jdoe",
  email: "jdoe@itba.edu.ar",
  fileNumber: 60000,
};

type FormData = {
  subject: string;
  content: string;
};

function Mail() {
    const { t } = useTranslation();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  return (
    <FormWrapper acceptCharset="utf-8" onSubmit={onSubmit}>
      <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t('Mail.form.title', {name: toUser.name, surname: toUser.surname})}
      </GeneralTitle>
      <FormLabel htmlFor="subject">{t('Mail.form.subject')}</FormLabel>

      <FormInput
        type="text"
        style={{ fontSize: "26px" }}
        {...register("subject", {
          required: true,
        })}
      />
      {errors.subject?.type === "required" && (
        <ErrorMessage>{t('Mail.error.subject.isRequired')}</ErrorMessage>
      )}
      <FormLabel htmlFor="content">{t('Mail.form.message')}</FormLabel>
      <FormArea
        style={{ width: "100%", resize: "none" }}
        cols={50}
        rows={10}
        {...register("content", {
          required: true,
        })}
      ></FormArea>
      {errors.content?.type === "required" && (
        <ErrorMessage>{t('Mail.error.message.isRequired')}</ErrorMessage>
      )}
      <FormButton>{t('Mail.form.sendMailButton')}</FormButton>
    </FormWrapper>
  );
}

export default Mail;
