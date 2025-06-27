import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gradient-to-b from-primary-800 to-primary-900 text-white relative">
      {/* Wave separator */}
      <div className="relative">
        <svg className="absolute -top-16 w-full h-16 text-primary-800" preserveAspectRatio="none" viewBox="0 0 1440 54" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
          <path d="M0 22L48 17.3C96 13 192 3 288 5.3C384 7 480 22 576 25.3C672 29 768 22 864 20.3C960 18 1056 22 1152 27.3C1248 33 1344 40 1392 43.3L1440 47V54H1392C1344 54 1248 54 1152 54C1056 54 960 54 864 54C768 54 672 54 576 54C480 54 384 54 288 54C192 54 96 54 48 54H0V22Z"></path>
        </svg>
      </div>

      {/* Newsletter subscription */}
      <div className="container mx-auto px-4 pb-8 pt-4">
        <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-6 shadow-lg border border-white/20 mb-12 animate-fadeIn">
          <div className="flex flex-col md:flex-row items-center justify-between">
            <div className="mb-4 md:mb-0 md:mr-8">
              <h3 className="text-xl font-bold font-heading mb-2">Subscribe to our newsletter</h3>
              <p className="text-secondary-light text-sm">Get the latest property updates and market insights delivered to your inbox.</p>
            </div>
            <div className="w-full md:w-auto flex-1 max-w-md">
              <form className="flex">
                <input type="email" placeholder="Your email address" className="form-input bg-white/5 border-white/20 text-white placeholder:text-white/50 flex-1" />
                <button type="submit" className="btn btn-accent ml-2 whitespace-nowrap">Subscribe</button>
              </form>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-10">
          <div className="animate-fadeIn">
            <div className="flex items-center mb-5 group">
              <svg className="w-10 h-10 mr-3 text-accent transform group-hover:scale-110 transition-transform duration-300 animate-floatUp" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
              </svg>
              <h3 className="text-2xl font-bold font-heading">Real Estate App</h3>
            </div>
            <p className="text-secondary-light leading-relaxed">
              Your comprehensive real estate solution for property comparison, loan calculations, and more. Making property decisions simpler and smarter.
            </p>
            <div className="mt-6">
              <span className="badge badge-accent">Trusted by 10,000+ users</span>
            </div>
          </div>

          <div className="animate-fadeIn animate-delay-100">
            <h4 className="text-lg font-bold mb-5 relative inline-block font-heading">
              <span className="relative z-10">Quick Links</span>
              <span className="absolute bottom-0 left-0 w-full h-1 bg-accent rounded-full -mb-1 transform origin-left"></span>
            </h4>
            <ul className="space-y-3">
              <li>
                <Link to="/" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Home
                </Link>
              </li>
              <li>
                <Link to="/properties" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Properties
                </Link>
              </li>
              <li>
                <Link to="/property-comparison" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Compare Properties
                </Link>
              </li>
              <li>
                <Link to="/loan-calculator" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Loan Calculator
                </Link>
              </li>
            </ul>
          </div>

          <div className="animate-fadeIn animate-delay-200">
            <h4 className="text-lg font-bold mb-5 relative inline-block font-heading">
              <span className="relative z-10">Resources</span>
              <span className="absolute bottom-0 left-0 w-full h-1 bg-accent rounded-full -mb-1 transform origin-left"></span>
            </h4>
            <ul className="space-y-3">
              <li>
                <Link to="/document-checklist" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Document Checklist
                </Link>
              </li>
              <li>
                <Link to="/service-charge-estimate" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Service Charges
                </Link>
              </li>
              <li>
                <Link to="/cost-breakdown" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Cost Breakdown
                </Link>
              </li>
              <li>
                <Link to="/rent-vs-buy" className="text-secondary-light hover:text-white transition-all duration-200 flex items-center group">
                  <span className="w-5 h-5 mr-2 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200">
                    <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </span>
                  Rent vs Buy
                </Link>
              </li>
            </ul>
          </div>

          <div className="animate-fadeIn animate-delay-300">
            <h4 className="text-lg font-bold mb-5 relative inline-block font-heading">
              <span className="relative z-10">Contact</span>
              <span className="absolute bottom-0 left-0 w-full h-1 bg-accent rounded-full -mb-1 transform origin-left"></span>
            </h4>
            <address className="text-secondary-light not-italic space-y-4">
              <div className="flex items-start group">
                <div className="w-8 h-8 mr-3 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200 mt-0.5 flex-shrink-0">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                  </svg>
                </div>
                <div>
                  <span className="font-medium">Smart Dubai</span><br />
                  Dubai, UAE
                </div>
              </div>
              <div className="flex items-center group">
                <div className="w-8 h-8 mr-3 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200 flex-shrink-0">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
                  </svg>
                </div>
                <a href="mailto:info@smartdubai.ae" className="hover:text-white transition-all duration-200 group-hover:translate-x-1 inline-block">info@smartdubai.ae</a>
              </div>
              <div className="flex items-center group">
                <div className="w-8 h-8 mr-3 flex items-center justify-center rounded-full bg-primary-700 group-hover:bg-accent transition-colors duration-200 flex-shrink-0">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path>
                  </svg>
                </div>
                <a href="tel:+97100000000" className="hover:text-white transition-all duration-200 group-hover:translate-x-1 inline-block">+971 00 000 0000</a>
              </div>
            </address>
          </div>
        </div>

        <div className="border-t border-white/10 mt-10 pt-8 flex flex-col md:flex-row justify-between items-center">
          <div className="flex flex-col md:flex-row items-center">
            <p className="text-secondary-light">&copy; {currentYear} Smart Dubai. All rights reserved.</p>
            <div className="flex space-x-4 mt-2 md:mt-0 md:ml-8">
              <Link to="/privacy-policy" className="text-secondary-light hover:text-white text-sm transition-all duration-200">Privacy Policy</Link>
              <Link to="/terms-of-service" className="text-secondary-light hover:text-white text-sm transition-all duration-200">Terms of Service</Link>
            </div>
          </div>
          <div className="flex space-x-4 mt-6 md:mt-0">
            <a href="#" className="social-icon" aria-label="Facebook">
              <span className="sr-only">Facebook</span>
              <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                <path fillRule="evenodd" d="M22 12c0-5.523-4.477-10-10-10S2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.988C18.343 21.128 22 16.991 22 12z" clipRule="evenodd" />
              </svg>
            </a>
            <a href="#" className="social-icon" aria-label="Twitter">
              <span className="sr-only">Twitter</span>
              <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                <path d="M8.29 20.251c7.547 0 11.675-6.253 11.675-11.675 0-.178 0-.355-.012-.53A8.348 8.348 0 0022 5.92a8.19 8.19 0 01-2.357.646 4.118 4.118 0 001.804-2.27 8.224 8.224 0 01-2.605.996 4.107 4.107 0 00-6.993 3.743 11.65 11.65 0 01-8.457-4.287 4.106 4.106 0 001.27 5.477A4.072 4.072 0 012.8 9.713v.052a4.105 4.105 0 003.292 4.022 4.095 4.095 0 01-1.853.07 4.108 4.108 0 003.834 2.85A8.233 8.233 0 012 18.407a11.616 11.616 0 006.29 1.84" />
              </svg>
            </a>
            <a href="#" className="social-icon" aria-label="Instagram">
              <span className="sr-only">Instagram</span>
              <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                <path fillRule="evenodd" d="M12.315 2c2.43 0 2.784.013 3.808.06 1.064.049 1.791.218 2.427.465a4.902 4.902 0 011.772 1.153 4.902 4.902 0 011.153 1.772c.247.636.416 1.363.465 2.427.048 1.067.06 1.407.06 4.123v.08c0 2.643-.012 2.987-.06 4.043-.049 1.064-.218 1.791-.465 2.427a4.902 4.902 0 01-1.153 1.772 4.902 4.902 0 01-1.772 1.153c-.636.247-1.363.416-2.427.465-1.067.048-1.407.06-4.123.06h-.08c-2.643 0-2.987-.012-4.043-.06-1.064-.049-1.791-.218-2.427-.465a4.902 4.902 0 01-1.772-1.153 4.902 4.902 0 01-1.153-1.772c-.247-.636-.416-1.363-.465-2.427-.047-1.024-.06-1.379-.06-3.808v-.63c0-2.43.013-2.784.06-3.808.049-1.064.218-1.791.465-2.427a4.902 4.902 0 011.153-1.772A4.902 4.902 0 015.45 2.525c.636-.247 1.363-.416 2.427-.465C8.901 2.013 9.256 2 11.685 2h.63zm-.081 1.802h-.468c-2.456 0-2.784.011-3.807.058-.975.045-1.504.207-1.857.344-.467.182-.8.398-1.15.748-.35.35-.566.683-.748 1.15-.137.353-.3.882-.344 1.857-.047 1.023-.058 1.351-.058 3.807v.468c0 2.456.011 2.784.058 3.807.045.975.207 1.504.344 1.857.182.466.399.8.748 1.15.35.35.683.566 1.15.748.353.137.882.3 1.857.344 1.054.048 1.37.058 4.041.058h.08c2.597 0 2.917-.01 3.96-.058.976-.045 1.505-.207 1.858-.344.466-.182.8-.398 1.15-.748.35-.35.566-.683.748-1.15.137-.353.3-.882.344-1.857.048-1.055.058-1.37.058-4.041v-.08c0-2.597-.01-2.917-.058-3.96-.045-.976-.207-1.505-.344-1.858a3.097 3.097 0 00-.748-1.15 3.098 3.098 0 00-1.15-.748c-.353-.137-.882-.3-1.857-.344-1.023-.047-1.351-.058-3.807-.058zM12 6.865a5.135 5.135 0 110 10.27 5.135 5.135 0 010-10.27zm0 1.802a3.333 3.333 0 100 6.666 3.333 3.333 0 000-6.666zm5.338-3.205a1.2 1.2 0 110 2.4 1.2 1.2 0 010-2.4z" clipRule="evenodd" />
              </svg>
            </a>
            <a href="#" className="social-icon" aria-label="LinkedIn">
              <span className="sr-only">LinkedIn</span>
              <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z" />
              </svg>
            </a>
            <a href="#" className="social-icon" aria-label="YouTube">
              <span className="sr-only">YouTube</span>
              <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                <path fillRule="evenodd" d="M23.498 6.186a3.016 3.016 0 0 0-2.122-2.136C19.505 3.545 12 3.545 12 3.545s-7.505 0-9.377.505A3.017 3.017 0 0 0 .502 6.186C0 8.07 0 12 0 12s0 3.93.502 5.814a3.016 3.016 0 0 0 2.122 2.136c1.871.505 9.376.505 9.376.505s7.505 0 9.377-.505a3.015 3.015 0 0 0 2.122-2.136C24 15.93 24 12 24 12s0-3.93-.502-5.814zM9.545 15.568V8.432L15.818 12l-6.273 3.568z" clipRule="evenodd" />
              </svg>
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
