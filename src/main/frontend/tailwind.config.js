/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#e6f2ff',
          100: '#cce5ff',
          200: '#99cbff',
          300: '#66b0ff',
          400: '#3396ff',
          light: '#4da6ff',
          DEFAULT: '#0080ff',
          600: '#0073e6',
          dark: '#0066cc',
          800: '#0059b3',
          900: '#004c99',
        },
        secondary: {
          50: '#fafbfc',
          light: '#f8f9fa',
          DEFAULT: '#e9ecef',
          dark: '#dee2e6',
          400: '#ced4da',
          500: '#adb5bd',
          600: '#6c757d',
          700: '#495057',
          800: '#343a40',
          900: '#212529',
        },
        accent: {
          light: '#ffd166',
          DEFAULT: '#ffc233',
          dark: '#ffb300',
        },
        success: {
          light: '#a3e635',
          DEFAULT: '#84cc16',
          dark: '#65a30d',
        },
        warning: {
          light: '#fdba74',
          DEFAULT: '#fb923c',
          dark: '#ea580c',
        },
        danger: {
          light: '#f87171',
          DEFAULT: '#ef4444',
          dark: '#dc2626',
        },
      },
      fontFamily: {
        sans: ['Inter', 'ui-sans-serif', 'system-ui', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica Neue', 'Arial', 'sans-serif'],
        heading: ['Poppins', 'ui-sans-serif', 'system-ui'],
      },
      animation: {
        fadeIn: 'fadeIn 0.5s ease-in-out',
        slideInLeft: 'slideInLeft 0.5s ease-in-out',
        slideInRight: 'slideInRight 0.5s ease-in-out',
        slideInUp: 'slideInUp 0.5s ease-in-out',
        slideInDown: 'slideInDown 0.5s ease-in-out',
        pulse: 'pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite',
        bounce: 'bounce 1s infinite',
        spin: 'spin 1s linear infinite',
        ping: 'ping 1s cubic-bezier(0, 0, 0.2, 1) infinite',
        floatUp: 'floatUp 3s ease-in-out infinite',
        shimmer: 'shimmer 2s linear infinite',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
        slideInLeft: {
          '0%': { transform: 'translateX(-20px)', opacity: '0' },
          '100%': { transform: 'translateX(0)', opacity: '1' },
        },
        slideInRight: {
          '0%': { transform: 'translateX(20px)', opacity: '0' },
          '100%': { transform: 'translateX(0)', opacity: '1' },
        },
        slideInUp: {
          '0%': { transform: 'translateY(20px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' },
        },
        slideInDown: {
          '0%': { transform: 'translateY(-20px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' },
        },
        pulse: {
          '0%, 100%': { opacity: '1' },
          '50%': { opacity: '0.5' },
        },
        bounce: {
          '0%, 100%': {
            transform: 'translateY(-5%)',
            animationTimingFunction: 'cubic-bezier(0.8, 0, 1, 1)',
          },
          '50%': {
            transform: 'translateY(0)',
            animationTimingFunction: 'cubic-bezier(0, 0, 0.2, 1)',
          },
        },
        floatUp: {
          '0%, 100%': {
            transform: 'translateY(0)',
          },
          '50%': {
            transform: 'translateY(-10px)',
          },
        },
        shimmer: {
          '0%': {
            backgroundPosition: '-1000px 0',
          },
          '100%': {
            backgroundPosition: '1000px 0',
          },
        },
      },
      boxShadow: {
        'card': '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
        'card-hover': '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
        'elevated': '0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)',
        'inner': 'inset 0 2px 4px 0 rgba(0, 0, 0, 0.06)',
        'focus': '0 0 0 3px rgba(0, 128, 255, 0.5)',
        'button': '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
        'button-hover': '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
      },
      borderRadius: {
        'xl': '0.75rem',
        '2xl': '1rem',
        '3xl': '1.5rem',
        '4xl': '2rem',
      },
      transitionDuration: {
        '400': '400ms',
      },
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic': 'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
      backdropBlur: {
        'xs': '2px',
      },
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
      {
        realestatelight: {
          "primary": "#0080ff",
          "primary-focus": "#0066cc",
          "primary-content": "#ffffff",
          "secondary": "#e9ecef",
          "secondary-focus": "#dee2e6",
          "secondary-content": "#212529",
          "accent": "#ffc233",
          "accent-focus": "#ffb300",
          "accent-content": "#212529",
          "neutral": "#343a40",
          "neutral-focus": "#212529",
          "neutral-content": "#ffffff",
          "base-100": "#ffffff",
          "base-200": "#f8f9fa",
          "base-300": "#e9ecef",
          "base-content": "#212529",
          "info": "#3396ff",
          "success": "#84cc16",
          "warning": "#fb923c",
          "error": "#ef4444",
        },
        realestateDark: {
          "primary": "#3396ff",
          "primary-focus": "#0080ff",
          "primary-content": "#ffffff",
          "secondary": "#343a40",
          "secondary-focus": "#495057",
          "secondary-content": "#ffffff",
          "accent": "#ffd166",
          "accent-focus": "#ffc233",
          "accent-content": "#212529",
          "neutral": "#212529",
          "neutral-focus": "#343a40",
          "neutral-content": "#ffffff",
          "base-100": "#1e1e1e",
          "base-200": "#2d2d2d",
          "base-300": "#3d3d3d",
          "base-content": "#f8f9fa",
          "info": "#3396ff",
          "success": "#84cc16",
          "warning": "#fb923c",
          "error": "#ef4444",
        }
      }
    ],
    darkTheme: "realestateDark",
  },
}
