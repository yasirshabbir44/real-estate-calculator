import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <nav className="bg-primary shadow-md sticky top-0 z-50">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <Link to="/" className="text-white text-2xl font-bold flex items-center">
            <svg className="w-8 h-8 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
            </svg>
            Real Estate App
          </Link>

          <div className="hidden md:flex space-x-6">
            <Link to="/" className="text-white hover:text-secondary-light transition-colors duration-200">Home</Link>
            <Link to="/properties" className="text-white hover:text-secondary-light transition-colors duration-200">Properties</Link>
            <Link to="/property-comparison" className="text-white hover:text-secondary-light transition-colors duration-200">Compare</Link>
            <Link to="/loan-calculator" className="text-white hover:text-secondary-light transition-colors duration-200">Loan Calculator</Link>
            <div className="relative group">
              <button className="text-white hover:text-secondary-light transition-colors duration-200">More</button>
              <div className="absolute left-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-10 hidden group-hover:block">
                <Link to="/document-checklist" className="block px-4 py-2 text-gray-800 hover:bg-primary hover:text-white transition-colors duration-200">Document Checklist</Link>
                <Link to="/service-charge-estimate" className="block px-4 py-2 text-gray-800 hover:bg-primary hover:text-white transition-colors duration-200">Service Charges</Link>
                <Link to="/cost-breakdown" className="block px-4 py-2 text-gray-800 hover:bg-primary hover:text-white transition-colors duration-200">Cost Breakdown</Link>
              </div>
            </div>
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden">
            <button 
              className="text-white focus:outline-none"
              onClick={toggleMenu}
              aria-label="Toggle menu"
            >
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
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
        <div className={`md:hidden ${isMenuOpen ? 'block' : 'hidden'} transition-all duration-300 ease-in-out`}>
          <div className="px-2 pt-2 pb-4 space-y-1">
            <Link to="/" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Home</Link>
            <Link to="/properties" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Properties</Link>
            <Link to="/property-comparison" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Compare</Link>
            <Link to="/loan-calculator" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Loan Calculator</Link>
            <Link to="/document-checklist" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Document Checklist</Link>
            <Link to="/service-charge-estimate" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Service Charges</Link>
            <Link to="/cost-breakdown" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md transition-colors duration-200">Cost Breakdown</Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
