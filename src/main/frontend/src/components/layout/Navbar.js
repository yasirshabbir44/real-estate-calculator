import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const location = useLocation();

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  // Add scroll effect
  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 10) {
        setScrolled(true);
      } else {
        setScrolled(false);
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  // Close mobile menu when route changes
  useEffect(() => {
    setIsMenuOpen(false);
  }, [location]);

  // Check if a link is active
  const isActive = (path) => {
    return location.pathname === path;
  };

  return (
    <nav className={`bg-gradient-to-r from-primary-800 to-primary sticky top-0 z-50 transition-all duration-400 ${scrolled ? 'shadow-elevated py-2' : 'shadow-md py-4'}`}>
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center">
          <Link to="/" className="text-white text-2xl font-bold flex items-center group">
            <svg className="w-8 h-8 mr-2 transform group-hover:scale-110 transition-transform duration-300 animate-pulse" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
            </svg>
            <span className="relative overflow-hidden font-heading">
              Real Estate App
              <span className="absolute bottom-0 left-0 w-full h-0.5 bg-accent transform scale-x-0 origin-left transition-transform duration-300 group-hover:scale-x-100"></span>
            </span>
          </Link>

          <div className="hidden md:flex space-x-8">
            <Link to="/" className={isActive('/') ? 'nav-link-active' : 'nav-link'}>Home</Link>
            <Link to="/properties" className={isActive('/properties') ? 'nav-link-active' : 'nav-link'}>Properties</Link>
            <Link to="/property-comparison" className={isActive('/property-comparison') ? 'nav-link-active' : 'nav-link'}>Compare</Link>
            <Link to="/loan-calculator" className={isActive('/loan-calculator') ? 'nav-link-active' : 'nav-link'}>Loan Calculator</Link>
            <Link to="/rent-vs-buy" className={isActive('/rent-vs-buy') ? 'nav-link-active' : 'nav-link'}>Rent vs Buy</Link>
            <div className="relative group">
              <button className="nav-link flex items-center">
                More
                <svg className="w-4 h-4 ml-1 transform group-hover:rotate-180 transition-transform duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"></path>
                </svg>
              </button>
              <div className="absolute left-0 mt-2 w-64 bg-white rounded-xl shadow-elevated py-2 z-10 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-300 transform origin-top scale-95 group-hover:scale-100 border border-gray-100">
                <Link to="/document-checklist" className="block px-4 py-3 text-secondary-700 hover:bg-primary-50 hover:text-primary transition-all duration-200 flex items-center rounded-lg mx-2 my-1">
                  <svg className="w-5 h-5 mr-2 text-primary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                  </svg>
                  Document Checklist
                </Link>
                <Link to="/service-charge-estimate" className="block px-4 py-3 text-secondary-700 hover:bg-primary-50 hover:text-primary transition-all duration-200 flex items-center rounded-lg mx-2 my-1">
                  <svg className="w-5 h-5 mr-2 text-primary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                  </svg>
                  Service Charges
                </Link>
                <Link to="/cost-breakdown" className="block px-4 py-3 text-secondary-700 hover:bg-primary-50 hover:text-primary transition-all duration-200 flex items-center rounded-lg mx-2 my-1">
                  <svg className="w-5 h-5 mr-2 text-primary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                  Cost Breakdown
                </Link>
                <div className="divider mx-2"></div>
                <div className="px-4 py-2">
                  <span className="badge badge-primary">New</span>
                  <p className="text-xs text-secondary-600 mt-1">Check out our new property alerts feature!</p>
                </div>
              </div>
            </div>
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden">
            <button 
              className="text-white focus:outline-none focus:ring-2 focus:ring-secondary-light rounded-full p-2 hover:bg-primary-dark transition-colors duration-200"
              onClick={toggleMenu}
              aria-label="Toggle menu"
            >
              <svg className="h-6 w-6 transition-transform duration-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                {isMenuOpen ? (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                ) : (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
                )}
              </svg>
            </button>
          </div>
        </div>

        {/* Mobile menu */}
        <div 
          className={`md:hidden overflow-hidden transition-all duration-400 ease-in-out ${isMenuOpen ? 'max-h-[32rem] opacity-100 mt-4' : 'max-h-0 opacity-0'}`}
        >
          <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-4 space-y-2 shadow-lg border border-white/20 animate-fadeIn">
            <Link to="/" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
              </svg>
              Home
            </Link>
            <Link to="/properties" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/properties') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path>
              </svg>
              Properties
            </Link>
            <Link to="/property-comparison" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/property-comparison') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>
              </svg>
              Compare
            </Link>
            <Link to="/loan-calculator" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/loan-calculator') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z"></path>
              </svg>
              Loan Calculator
            </Link>
            <Link to="/rent-vs-buy" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/rent-vs-buy') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              Rent vs Buy
            </Link>

            <div className="divider my-2 bg-white/20"></div>
            <p className="text-white/80 px-4 text-sm font-medium">Additional Tools</p>

            <Link to="/document-checklist" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/document-checklist') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
              </svg>
              Document Checklist
            </Link>
            <Link to="/service-charge-estimate" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/service-charge-estimate') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              Service Charges
            </Link>
            <Link to="/cost-breakdown" className={`block px-4 py-3 rounded-xl transition-all duration-200 flex items-center ${isActive('/cost-breakdown') ? 'bg-primary-dark text-white' : 'text-white hover:bg-primary-dark/50'}`}>
              <svg className="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
              </svg>
              Cost Breakdown
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
