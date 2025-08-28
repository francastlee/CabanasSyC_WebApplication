export interface CardType {
  title: string;
  subtitle?: string;
  src: string;
  href?: string;
}

export interface AnimationProps {
  distance: number;
  direction: 'vertical' | 'horizontal';
  reverse: boolean;
  config: { tension: number; friction: number };
  initialOpacity: number;
  animateOpacity: boolean;
  scale: number;
  threshold: number;
}