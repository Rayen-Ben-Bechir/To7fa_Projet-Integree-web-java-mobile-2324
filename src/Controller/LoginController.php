<?php

namespace App\Controller;

use App\Entity\Participants;
use App\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorageInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use App\Form\LoginType;
use App\Repository\ParticipantsRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\TestRepository;
use App\Service\SmsGenerator;
use App\Service\UtilisateurService;
use Doctrine\ORM\EntityManager;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Core\User\UserInterface;
use Twilio\Exceptions\TwilioException;
use Twilio\Rest\Client;
 
class LoginController extends AbstractController

{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    #[Route('/check-role', name: 'check_role')]
    public function checkRole(
        Request $request,
        UserRepository $repo,
        UtilisateurService $serviceMail,
        RequestStack $session
             ): Response {
        if ($request->isMethod('POST')) {
            $email = $request->request->get('email');
            $password = $request->request->get('password');
            $user = $repo->findOneBy(['userEmail' => $email]); // Corrected field name
    



 // Check if the provided credentials match the admin credentials
 if ($email === 'admin@gmail.com' && $password === 'admin') {
    // Create a fake user object with admin role for authorization
    $fakeAdminUser = new User();
    $fakeAdminUser->setUserEmail($email);
    $fakeAdminUser->setUserPassword($password);
    $fakeAdminUser->setuserRole('ROLE_ADMIN');
 
    // Store the fake admin user in session for authorization
    $session->getSession('user', $fakeAdminUser);

    // Redirect to the admin dashboard or any other admin-specific page
    return $this->render('admin/homeAdmin.html.twig', [
        'user' => $fakeAdminUser,
        'users' => $repo->findAll(),
    ]);}


    if ($user) {
             if ($user->isBanned()) {
                // User is banned, deny login
                $this->addFlash('error', 'Votre compte a été banni.');
                return $this->redirectToRoute('app_connect');
            }

        // Compare the plaintext password with the hashed password
        if (password_verify($password, $user->getUserPassword())) {
            // Password is correct
            $session->getSession('user', $user);

            if ($user->getuserRole() === "ROLE_USER") {
                return $this->render('user/home.html.twig', [
                    'user' => $user,
                ]);
            } elseif ($user->getuserRole() === "ROLE_ADMIN") {
                return $this->redirectToRoute('admin_dashboard');
            
            }
        } else {
            // Password is incorrect, send an email
            $subject = "Incorrect Password Attempt";
            $message = "Someone attempted to log in with an incorrect password using your email address.";
 
            // Display error message and redirect
            $this->addFlash('error', 'Adresse e-mail ou mot de passe incorrect.');
            return $this->redirectToRoute('app_connect');
        }
    }
}

return $this->redirectToRoute('app_connect');}   




    #[Route('/connect', name: 'app_connect')]
    public function login(Request $request, AuthenticationUtils $authenticationUtils)
    {
        $form = $this->createForm(LoginType::class);


        return $this->render('login/login.html.twig', [
            'form' => $form->createView(),
          
        ]);
    }
  

    
    #[Route('/logout', name: 'app_logout')]
    public function logout(
        Request $request,
        TokenStorageInterface $tokenStorage
    ): RedirectResponse {
        // Gérez la déconnexion de l'utilisateur ici
        $tokenStorage->setToken(null);

        // Redirigez l'utilisateur vers une page après la déconnexion
        return new RedirectResponse($this->generateUrl('app_connect'));
    }



    #[Route('/admin/dashboard', name: 'admin_dashboard')]
    public function adminDashboard(Request $request, UserRepository $userRepository): Response
    {
        // Fetch all users from the database
        $users = $userRepository->findAll();
        return $this->render('admin/homeAdmin.html.twig', [
            'users' => $users,
        ]);
    }


    
    #[Route('/admin/ban/{userId}', name: 'admin_ban_user')]
public function banUser(int $userId, UserRepository $userRepository, EntityManagerInterface $entityManager): Response
{
    $user = $userRepository->find($userId);

    if (!$user) {
        throw $this->createNotFoundException('Utilisateur non trouvé');
    }

    // Bannir l'utilisateur en définissant isBanned à true
    $user->setBanned(true);
    $entityManager->flush();

    $this->addFlash('success', 'L\'utilisateur a été banni avec succès.');

    return $this->redirectToRoute('admin_dashboard');
}
 

















#[Route('/forgot-password', name: 'forgot_password')]
public function forgotPassword(
    Request $request,
    UtilisateurService $utilisateurService,
    UserRepository $userRepository,
    SessionInterface $session,
    Client $twilio, SmsGenerator $smsGenerator
): Response {
    if ($request->isMethod('POST')) {
        $email = $request->request->get('email');
        $number = $request->request->get('number');

        // Validate phone number
             $this->addFlash('error', 'Invalid phone number.');
             return $this->render('sms/index.html.twig',['smsSent'=>false]);
        

        $user = $userRepository->findOneBy(['userEmail' => $email]);
 }
 

    return $this->render('sms/index.html.twig',['smsSent'=>false]);
}
/*

private function sendCodeSMS(string $phoneNumber, string $code): void
{
    // Your Twilio Account SID, Auth Token, and Twilio phone number
    $twilioSID = 'AC0d8303dd6dbab00851b2ab79d155897c';
    $twilioAuthToken = '47fa86aeee133f8cbae86fc96af94831';
    $twilioPhoneNumber = '+13345648011'; // Your Twilio phone number

    // Create a Twilio client
    $twilio = new Client($twilioSID, $twilioAuthToken);

    try {
        $twilio->messages
            ->create($phoneNumber, [
                'from' => $twilioPhoneNumber,
                'body' => 'Your verification code is: ' . $code,
            ]);

        // Display custom successful message
        $this->addFlash('success', 'Code sent successfully!');
    } catch (TwilioException $e) {
        // Display custom error message
        $this->addFlash('error', 'Error sending code: ' . $e->getMessage());
    }
}
*/
 

#[Route('/verify-code', name: 'verify_code')]
public function verifyCode(
    Request $request,
    UserRepository $userRepository,
    SessionInterface $session
): Response {
 
    $sentCode = $session->get('sent_code');
    $enteredCode = $request->request->get('code');
    $newPassword = $request->request->get('new_password');
    $email = $request->request->get('email');
    if (trim($sentCode) === trim($enteredCode)) {
        // Code is valid, reset the password
        $user = $userRepository->findOneBy(['userEmail' => $email]);

        if ($user instanceof User) {
            // Hash the new password
            $hashedPassword = password_hash($newPassword, PASSWORD_DEFAULT);
            $user->setUserPassword($hashedPassword);

            // Persist the updated user
            $this->entityManager->persist($user);
            $this->entityManager->flush();

            // Redirect to login page or a success page
            $this->addFlash('success', 'Password reset successful.');
            return $this->redirectToRoute('app_connect');
        } else {
            $this->addFlash('error', 'User not found.');
        }
    } else {
        // Code is invalid, display an error message
     }

    // Display the form again with an error message
    return $this->render('login/verify_code.html.twig', ['email' => $email]);
}}