"use client";

import { useState } from "react";
import { AnimatedButton } from "../common/AnimatedButton";
import { FaEnvelope } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import { sendContactForm } from "../../api/Contact";
import { toast } from "react-toastify";

export default function ContactSection() {
  const { t } = useTranslation("contact");

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    message: "",
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validateEmail = (email: string) => /\S+@\S+\.\S+/.test(email);
  const validatePhone = (phone: string) => /^\d{8,}$/.test(phone);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!formData.firstName.trim()) {
      toast.error(t("validation.emptyFirstname"));
      return;
    }

    if (!formData.lastName.trim()) {
      toast.error(t("validation.emptyLastname"));
      return;
    }

    if (!validateEmail(formData.email)) {
      toast.error(t("validation.invalidEmail"));
      return;
    }

    if (!validatePhone(formData.phone)) {
      toast.error(t("validation.invalidPhone"));
      return;
    }

    if (!formData.message.trim()) {
      toast.error(t("validation.emptyMessage"));
      return;
    }

    try {
      await sendContactForm({
        ...formData,
        date: new Date().toISOString().split("T")[0],
      });

      toast.success(t("validation.formSuccess") || "Message sent successfully!");

      setFormData({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        message: "",
      });
    } catch (error: any) {
      toast.error(t("validation.formError") || "Error sending message.");
    }
  };

  return (
    <section className="w-full bg-[#4B2A1F] py-16 px-4 flex flex-col items-center relative select-none">
      <div
        className="absolute top-0 left-0 w-full h-40 bg-gradient-to-b from-[#1a3a17] to-transparent pointer-events-none z-10"
        aria-hidden="true"
      />
      <div
        className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#1a3a17] to-transparent pointer-events-none z-10"
        aria-hidden="true"
      />
      <h2 className="text-white text-4xl md:text-6xl font-cinzel text-glow-green text-center tracking-tight mb-12 mt-30">
        {t("title")}
      </h2>

      <div className="flex flex-col md:flex-row gap-8 w-full max-w-7xl items-start justify-center mb-40">
        <div className="w-full md:w-1/2 h-[300px] md:h-[500px] rounded-3xl overflow-hidden shadow-lg">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1166.4774233073629!2d-84.66627958520714!3d10.45749659028578!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8fa00df6b7725997%3A0x6ee805c4cb524748!2sCaba%C3%B1as%20S%20y%20C!5e0!3m2!1ses-419!2scr!4v1753991409066!5m2!1ses-419!2scr"
            className="w-full h-full border-0"
            allowFullScreen
            loading="lazy"
            referrerPolicy="no-referrer-when-downgrade"
            title="Ubicación de Cabañas SyC"
          />
        </div>

        <form
          onSubmit={handleSubmit}
          className="bg-[#856D5D] w-full md:w-1/2 p-4 md:p-8 rounded-3xl shadow-lg space-y-4 md:space-y-6 h-auto md:h-[500px]"
        >
          <h3 className="text-white text-lg md:text-xl font-semibold text-center">
            {t("cardTitle")}
          </h3>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <InputField
              label={t("firstname")}
              name="firstName"
              type="text"
              placeholder={t("inputFirstname")}
              value={formData.firstName}
              onChange={handleChange}
              icon="fa-regular fa-address-card"
            />
            <InputField
              label={t("lastname")}
              name="lastName"
              type="text"
              placeholder={t("inputLastname")}
              value={formData.lastName}
              onChange={handleChange}
              icon="fa-solid fa-user"
            />
            <InputField
              label={t("email")}
              name="email"
              type="email"
              placeholder={t("inputEmail")}
              value={formData.email}
              onChange={handleChange}
              icon="fa-regular fa-envelope"
            />
            <InputField
              label={t("phone")}
              name="phone"
              type="tel"
              placeholder={t("inputPhone")}
              value={formData.phone}
              onChange={handleChange}
              icon="fa-solid fa-phone"
            />
          </div>

          <div>
            <label htmlFor="message" className="block text-sm text-white mb-1">
              {t("message")}
            </label>
            <textarea
              name="message"
              id="message"
              value={formData.message}
              onChange={handleChange}
              rows={4}
              placeholder={t("inputMessage")}
              className="w-full p-2.5 text-sm text-dark rounded-lg bg-white pl-5"
              required
            />
          </div>

          <div className="pt-2">
            <AnimatedButton icon={<FaEnvelope />} className="text-white w-full md:w-1/3 bg-[#1a3a17]">
              {t("button")}
            </AnimatedButton>
          </div>
        </form>
      </div>
    </section>
  );
}

function InputField({
  label,
  name,
  type,
  placeholder,
  value,
  onChange,
  icon,
}: {
  label: string;
  name: string;
  type: string;
  placeholder: string;
  value: string;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
  icon: string;
}) {
  return (
    <div className="w-full">
      <label htmlFor={name} className="block text-sm text-white mb-1">
        {label}
      </label>
      <div className="relative">
        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
          <i className={`${icon} text-white`} />
        </div>
        <input
          type={type}
          name={name}
          id={name}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          className="bg-white text-dark text-sm rounded-lg w-full p-2.5 pl-5"
          required
        />
      </div>
    </div>
  );
}
