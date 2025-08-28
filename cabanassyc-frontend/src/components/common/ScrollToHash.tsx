"use client";
import { useEffect } from "react";
import { useLocation } from "react-router-dom";

interface Props {
  offset?: number;
}

export default function ScrollToHash({ offset = 0 }: Props) {
  const { hash } = useLocation();

  useEffect(() => {
    if (!hash) return;
    const id = hash.slice(1);

    const doScroll = () => {
      const el = document.getElementById(id);
      if (!el) return false;
      const y = el.getBoundingClientRect().top + window.pageYOffset - offset;
      window.scrollTo({ top: y, behavior: "smooth" });
      return true;
    };

    if (!doScroll()) {
      const t = setTimeout(doScroll, 120);
      return () => clearTimeout(t);
    }
  }, [hash, offset]);

  return null;
}
