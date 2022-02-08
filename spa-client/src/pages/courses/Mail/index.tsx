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
import React, { useEffect, useState } from "react";
import { handleService } from "../../../scripts/handleService";
import { userService } from "../../../services";
import { useNavigate, useParams } from "react-router-dom";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
import { UserModel } from "../../../types";
import LoadableData from "../../../components/LoadableData";
//

type FormData = {
  subject: string;
  content: string;
};

function Mail() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [user, setUser] = useState<UserModel | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(true);
  const { courseId, userId } = useParams();

  useEffect(() => {
    setIsLoading(true);
    handleService(
      userService.getUserById(userId ? parseInt(userId) : -1),
      navigate,
      (user) => {
        setUser(user);
      },
      () => setIsLoading(false)
    );
  }, []);

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
    <FormWrapper
      acceptCharset="utf-8"
      onSubmit={onSubmit}
      style={{ alignItems: isLoading ? "center" : "stretch" }}
    >
      <LoadableData isLoading={isLoading}>
        <GeneralTitle
          style={{
            color: "#176961",
            alignSelf: "center",
          }}
        >
          {t("Mail.form.title", { name: user?.name, surname: user?.surname })}
        </GeneralTitle>
        <FormLabel htmlFor="subject">{t("Mail.form.subject")}</FormLabel>

        <FormInput
          type="text"
          style={{ fontSize: "26px", width: "95%" }}
          {...register("subject", {
            required: true,
          })}
        />
        {errors.subject?.type === "required" && (
          <ErrorMessage>{t("Mail.error.subject.isRequired")}</ErrorMessage>
        )}
        <FormLabel htmlFor="content">{t("Mail.form.message")}</FormLabel>
        <FormArea
          style={{ width: "95%", resize: "none" }}
          cols={50}
          rows={10}
          {...register("content", {
            required: true,
          })}
        ></FormArea>
        {errors.content?.type === "required" && (
          <ErrorMessage>{t("Mail.error.message.isRequired")}</ErrorMessage>
        )}
        <FormButton>{t("Mail.form.sendMailButton")}</FormButton>
      </LoadableData>
    </FormWrapper>
  );
}

export default Mail;
