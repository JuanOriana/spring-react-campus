import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  ErrorMessage,
  FormButton,
  FormInput,
  FormLabel,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import React, { useState } from "react";
import { useForm } from "react-hook-form";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

type FormData = {
  fileNumber: number;
  name: string;
  surname: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
};

function AdminNewUser() {
  const { t } = useTranslation();
  const [isFileNumberDuplicated, setIsFileNumberDuplicated] = useState(false);
  const [isUsernameDuplicated, setIsUsernameDuplicated] = useState(false);
  const [isEmailDuplicated, setIsEmailDuplicated] = useState(false);
  const nextFileNumber = 42;

  const {
    register,
    handleSubmit,
    reset,
    setError,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    console.log(data);
    if (data.password !== data.confirmPassword) {
      setError("confirmPassword", {
        type: "repeated",
        message: t('AdminNewUser.error.passwordsMustMatch'),
      });
    } else {
      reset();
    }
  });
  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        acceptCharset="utf-8"
        style={{ margin: "0px 40px 40px 40px" }}
        onSubmit={onSubmit}
        method="post"
      >
        <GeneralTitle
          className="announcement-title"
          style={{ color: "#176961", alignSelf: "center" }}
        >
          {t('AdminNewUser.form.title')}
        </GeneralTitle>
        <FormLabel htmlFor="fileNumber">{t('AdminNewUser.form.fileNumber')}</FormLabel>
        <FormInput
          type="number"
          min="0"
          style={{ fontSize: "26px" }}
          value={nextFileNumber}
          {...register("fileNumber", {
            required: true,
            min: 0,
          })}
        />
        {errors.fileNumber?.type === "required" && (
          <ErrorMessage>{t('AdminNewUser.error.fileNumber.isRequired')}</ErrorMessage>
        )}
        {errors.fileNumber?.type === "min" && (
          <ErrorMessage>{t('AdminNewUser.error.fileNumber.positiveInteger')}</ErrorMessage>
        )}
        {isFileNumberDuplicated && (
          <ErrorMessage>{t('AdminNewUser.error.fileNumber.exists')}</ErrorMessage>
        )}
        <FormLabel htmlFor="name">{t('AdminNewUser.form.name')}</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("name", {
            required: true,
            pattern: {
              value: /[a-zA-Z ']+/,
              message: t('AdminNewUser.error.name.onlyLetters'),
            },
          })}
        />
        {errors.name?.type === "required" && (
          <ErrorMessage>{t('AdminNewUser.error.name.isRequired')}</ErrorMessage>
        )}
        {errors.name?.message && (
          <ErrorMessage> {errors.name?.message} </ErrorMessage>
        )}
        <FormLabel htmlFor="surname">{t('AdminNewUser.form.surname')}</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("surname", {
            required: true,
            pattern: {
              value: /[a-zA-Z ']+/,
              message: t('AdminNewUser.error.surname.onlyLetters'),
            },
          })}
        />
        {errors.surname?.type === "required" && (
          <ErrorMessage>{t('AdminNewUser.error.surname.isRequired')}</ErrorMessage>
        )}
        {errors.surname?.message && (
          <ErrorMessage> {errors.surname?.message} </ErrorMessage>
        )}
        <FormLabel htmlFor="username">{t('AdminNewUser.form.username')}</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("username", {
            required: true,
            validate: {
              length: (username) =>
                username.length >= 6 && username.length <= 50,
            },
            pattern: {
              value: /[a-zA-Z0-9]+/,
              message: t('AdminNewUser.error.username.pattern'),
            },
          })}
        />
        {errors.username?.type === "required" && (
          <ErrorMessage>{t('AdminNewUser.error.username.isRequired')}</ErrorMessage>
        )}
        {errors.username?.type === "length" && (
          <ErrorMessage>
            {t('AdminNewUser.error.username.length')}
          </ErrorMessage>
        )}
        {errors.username?.message && (
          <ErrorMessage> {errors.username?.message} </ErrorMessage>
        )}
        {isUsernameDuplicated && (
          <ErrorMessage>{t('AdminNewUser.error.username.exists')}</ErrorMessage>
        )}
        <FormLabel htmlFor="email">{t('AdminNewUser.form.email')}</FormLabel>
        <FormInput
          type="email"
          style={{ fontSize: "26px" }}
          {...register("email", {
            required: true,
            pattern: {
              value:
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
              message: t('AdminNewUser.error.email.pattern'),
            },
          })}
        />
        {errors.email?.message && (
          <ErrorMessage> {errors.email?.message} </ErrorMessage>
        )}
        {errors.email?.type === "required" && (
            <ErrorMessage>{t('AdminNewUser.error.email.isRequired')}</ErrorMessage>
        )}
        {isEmailDuplicated && <ErrorMessage>{t('AdminNewUser.error.email.exists')}</ErrorMessage>}
        <FormLabel htmlFor="password">{t('AdminNewUser.form.password')}</FormLabel>
        <FormInput
          type="password"
          style={{ fontSize: "26px" }}
          {...register("password", {
            required: true,
            validate: {
              length: (password) =>
                password.length >= 8 && password.length <= 50,
            },
            pattern: {
              value: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
              message:
                  t('AdminNewUser.error.password.pattern'),
            },
          })}
        />
        {errors.password?.type === "required" && (
          <ErrorMessage>{t('AdminNewUser.error.password.isRequired')}</ErrorMessage>
        )}
        {errors.password?.type === "length" && (
          <ErrorMessage>
            {t('AdminNewUser.error.password.length')}
          </ErrorMessage>
        )}
        {errors.password?.message && (
          <ErrorMessage> {errors.password?.message} </ErrorMessage>
        )}
        <FormLabel htmlFor="confirmPassword">{t('AdminNewUser.form.confirmPassword')}</FormLabel>
        <FormInput
          type="password"
          style={{ fontSize: "26px" }}
          {...register("confirmPassword", {})}
        />
        {errors.confirmPassword?.message && (
          <ErrorMessage> {errors.confirmPassword?.message} </ErrorMessage>
        )}
        <FormButton>{t('AdminNewUser.form.createButton')}</FormButton>
      </FormWrapper>
    </>
  );
}

export default AdminNewUser;
